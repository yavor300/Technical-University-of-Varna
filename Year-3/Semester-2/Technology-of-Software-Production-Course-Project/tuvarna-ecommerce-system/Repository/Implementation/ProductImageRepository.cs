using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class ProductImageRepository : IProductImageRepository
    {

        private readonly EcommerceDbContext _context;

        public ProductImageRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public Task<ProductImage> CreateAsync(ProductImage entity)
        {
            throw new NotImplementedException();
        }
    }
}
