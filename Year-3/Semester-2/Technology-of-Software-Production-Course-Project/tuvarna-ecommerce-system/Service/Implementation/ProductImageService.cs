using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;
using tuvarna_ecommerce_system.Repository.Implementation;
using tuvarna_ecommerce_system.Utils;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class ProductImageService : IProductImageService
    {

        private readonly IProductImageRepository _repository;
        private readonly ILogger<ProductImageService> _logger;

        public ProductImageService(IProductImageRepository repository, ILogger<ProductImageService> logger)
        {
            _repository = repository;
            _logger = logger;
        }

        public async Task<ProductImageReadDTO> CreateAsync(ProductImageCreateDTO dto)
        {
            try
            {
                var image = new ProductImage
                {
                    ImageUrl = dto.ImageUrl,
                    ProductId = dto.ProductId
                };

                image = await _repository.CreateAsync(image);

                var response = new ProductImageReadDTO
                {
                    Id = image.Id,
                    ImageUrl = image.ImageUrl
                };

                return response;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }
    }
}
