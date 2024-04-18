using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface IProductInventoryRepository
    {

        Task<ProductInventory> CreateAsync(ProductInventory productInventory);

    }
}
