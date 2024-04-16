using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface IProductImageService
    {

        Task<ProductImageReadDTO> CreateAsync(ProductImageCreateDTO dto);
    }
}
