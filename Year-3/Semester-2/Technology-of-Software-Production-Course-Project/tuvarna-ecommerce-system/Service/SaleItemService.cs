using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;

namespace tuvarna_ecommerce_system.Service
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

        public async Task<SaleItemReadDTO> CreateAsync(SaleItemCreateDTO dto)
        {
            using (var transaction = _context.Database.BeginTransaction())
            {
                try
                {

                    Product productToSell =
                        await _productRepository.GetByIdAsync(dto.ProductId);

                    int totalStockAvailable = productToSell.Inventories.Sum(inventory => inventory.StockQuantity);

                    if (dto.QuantitySold > totalStockAvailable)
                    {
                        throw new InvalidOperationException("Requested quantity exceeds available stock.");
                    }

                    decimal totalPrice = 0m;
                    int quantityToDeduct = dto.QuantitySold;
                    foreach (var inventory in productToSell.Inventories)
                    {
                        if (quantityToDeduct <= 0) break;

                        int deduct = Math.Min(quantityToDeduct, inventory.StockQuantity);
                        inventory.StockQuantity -= deduct; // Deduct from the current inventory
                        quantityToDeduct -= deduct;
                        decimal pricePerUnit = inventory.DiscountPrice ?? inventory.Price;
                        totalPrice += deduct * pricePerUnit;

                        //_context.Update(inventory);
                    }


                    var saleItem = new SaleItem
                    {
                        QuantitySold = dto.QuantitySold,
                        ProductId = dto.ProductId
                    };

                    var createdSaleItem = await _repository.CreateAsync(saleItem);

                    await _context.SaveChangesAsync();

                    transaction.Commit();

                    var result = new SaleItemReadDTO
                    {
                        Id = createdSaleItem.Id
                    };

                    return result;
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
                    _logger.LogError(ex, "Failed to create sale item record.");
                    throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
                }
            }
        }
    }
}
