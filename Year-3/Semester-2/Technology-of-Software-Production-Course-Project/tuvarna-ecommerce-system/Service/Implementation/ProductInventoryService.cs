using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class ProductInventoryService : IProductInventoryService
    {

        private readonly IProductInventoryRepository _repository;
        private readonly ILogger<ProductInventoryService> _logger;
        private readonly IProductService _productService;

        public ProductInventoryService(IProductInventoryRepository repository, ILogger<ProductInventoryService> logger, IProductService productService)
        {
            _repository = repository;
            _logger = logger;
            _productService = productService;
        }

        public async Task<ProductInventoryReadDTO> CreateAsync(ProductInventoryCreateDTO dto)
        {

            try
            {

                await _productService.GetByIdAsync(new ProductGetByIdDTO { Id = dto.ProductId });

                var importDate = dto.ImportDate ?? DateTime.UtcNow;

                var newInventory = new ProductInventory
                {
                    Price = dto.Price,
                    DiscountPrice = dto.DiscountPrice,
                    StockQuantity = dto.StockQuantity,
                    ImportDate = importDate,
                    ProductId = dto.ProductId
                };

                var createdInventory = await _repository.CreateAsync(newInventory);

                return new ProductInventoryReadDTO
                {
                    Id = createdInventory.Id,
                    Price = createdInventory.Price,
                    DiscountPrice = createdInventory.DiscountPrice,
                    StockQuantity = createdInventory.StockQuantity,
                    ImportDate = createdInventory.ImportDate,
                    ProductId = createdInventory.ProductId
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
    }
}
