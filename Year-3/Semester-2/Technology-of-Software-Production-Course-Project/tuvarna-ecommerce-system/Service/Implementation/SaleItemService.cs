using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class SaleItemService : ISaleItemService
    {

        private readonly EcommerceDbContext _context;
        private readonly ISaleItemRepository _repository;
        private readonly ILogger<SaleItemService> _logger;
        private readonly IProductRepository _productRepository;

        public SaleItemService(ISaleItemRepository repository, ILogger<SaleItemService> logger,
            IProductRepository productRepository, EcommerceDbContext context)
        {
            _repository = repository;
            _logger = logger;
            _productRepository = productRepository;
            _context = context;
        }

        public async Task<SaleItemReadDTO> AssociateWithSale(int SoldItemId, int SaleId)
        {

            try
            {
                var associated = await _repository.AssociateWithSale(SoldItemId, SaleId);

                return new SaleItemReadDTO
                {
                    Id = associated.Id,
                    ProductId = associated.ProductId,
                    QuantitySold = associated.QuantitySold
                };
            }
            catch (EntityNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<List<SaleItemReadDTO>> CreateAsync(List<SaleItemCreateDTO> dtos, int SaleId)
        {
            var saleItemsRead = new List<SaleItemReadDTO>();
            using (var transaction = _context.Database.BeginTransaction())
            {
                try
                {
                    foreach (var dto in dtos)
                    {
                        Product productToSell = await _productRepository.GetByIdAsync(dto.ProductId);
                        int totalStockAvailable = productToSell.Inventories.Sum(inventory => inventory.StockQuantity);

                        if (dto.QuantitySold > totalStockAvailable)
                        {
                            throw new InvalidOperationException(
                                $"Requested quantity for product ID {dto.ProductId} exceeds available stock.");
                        }

                        decimal totalPrice = 0m;
                        int quantityToDeduct = dto.QuantitySold;
                        foreach (var inventory in productToSell.Inventories)
                        {
                            if (quantityToDeduct <= 0) break;

                            int deduct = Math.Min(quantityToDeduct, inventory.StockQuantity);
                            inventory.StockQuantity -= deduct;
                            quantityToDeduct -= deduct;
                            decimal pricePerUnit = inventory.DiscountPrice ?? inventory.Price;
                            totalPrice += deduct * pricePerUnit;

                            // Update inventory
                            // _context.Update(inventory);
                        }

                        var saleItem = new SaleItem
                        {
                            QuantitySold = dto.QuantitySold,
                            ProductId = dto.ProductId,
                            SaleId = SaleId
                        };

                        var createdSaleItem = await _repository.CreateAsync(saleItem);
                        saleItemsRead.Add(new SaleItemReadDTO
                        {
                            Id = createdSaleItem.Id,
                            ProductId = createdSaleItem.ProductId,
                            QuantitySold = createdSaleItem.QuantitySold,
                            TotalPrice = totalPrice
                        });
                    }

                    await _context.SaveChangesAsync();
                    transaction.Commit();

                    return saleItemsRead;
                }
                catch (EntityNotFoundException ex)
                {
                    transaction.Rollback();
                    _logger.LogError(ex, ex.Message);
                    throw;
                }
                catch (InvalidOperationException ex)
                {
                    transaction.Rollback();
                    _logger.LogError(ex, ex.Message);
                    throw;
                }
                catch (Exception ex)
                {
                    transaction.Rollback();
                    _logger.LogError(ex, "Failed to create sale items.");
                    throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
                }
            }
        }

    }
}
