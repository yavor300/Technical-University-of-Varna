using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface IProductInventoryService
    {

        Task<ProductInventoryReadDTO> CreateAsync(ProductInventoryCreateDTO dto);
    }
}
