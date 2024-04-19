using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface ISaleItemRepository
    {

        Task<SaleItem> CreateAsync(SaleItem saleItem);

        Task<SaleItem> AssociateWithSale(int SoldItemId, int SaleId);

        //Task<SaleItem> GetByIdAsync(int id);
    }
}
