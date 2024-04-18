using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class ProductInventoryRepository : IProductInventoryRepository
    {

        private readonly EcommerceDbContext _context;

        public ProductInventoryRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<ProductInventory> CreateAsync(ProductInventory productInventory)
        {

            _context.ProductInventory.Add(productInventory);
            await _context.SaveChangesAsync();
            return productInventory;
        }
    }
}
