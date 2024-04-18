using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ISaleItemService
    {

        Task<SaleItemReadDTO> CreateAsync(SaleItemCreateDTO dto);
    }
}
