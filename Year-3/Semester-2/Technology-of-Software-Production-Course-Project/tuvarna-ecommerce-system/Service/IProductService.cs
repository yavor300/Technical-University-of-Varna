using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface IProductService
    {

        Task<ProductReadDTO> AddAsync(ProductCreateDTO dto);

        Task<ProductReadDTO> GetByIdAsync(ProductGetByIdDTO dto);

        Task<ProductReadDTO> PatchAsync(ProductPatchDTO dto);

        Task<ProductReadAllDTO> GetByCategoryName(string name);
    }
}
