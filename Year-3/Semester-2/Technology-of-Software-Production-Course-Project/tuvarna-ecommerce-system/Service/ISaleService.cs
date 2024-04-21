using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ISaleService
    {

        Task<SaleReadDTO> CreateAsync(SaleCreateDTO dto);

    }
}
