using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ISaleItemService
    {

        Task<List<SaleItemReadDTO>> CreateAsync(List<SaleItemCreateDTO> dtos, int SaleId);

        Task<SaleItemReadDTO> AssociateWithSale(int SoldItemId, int SaleId);
    }
}
