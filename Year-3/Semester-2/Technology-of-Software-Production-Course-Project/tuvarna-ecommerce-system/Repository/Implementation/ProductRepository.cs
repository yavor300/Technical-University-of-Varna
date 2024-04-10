using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class ProductRepository : IProductRepository
    {

        private readonly EcommerceDbContext _context;

        public ProductRepository(EcommerceDbContext context)
        {
            _context = context;
        }


        public async Task<Product> CreateAsync(Product product, List<Tag> productTags)
        {

            product.Sku = GenerateUniqueSku();
            // Ensure EF knows these tags are existing entities
            foreach (var tag in productTags)
            {
                if (_context.Entry(tag).State == EntityState.Detached)
                {
                    _context.Tags.Attach(tag);
                }
            }

            product.Tags = productTags;
            _context.Products.Add(product);
            await _context.SaveChangesAsync();

            return product;
        }

        private string GenerateUniqueSku()
        {
            var random = new Random();
            string sku;

            do
            {
                sku = Enumerable.Range(0, 6).Select(x => random.Next(0, 10).ToString()).Aggregate((acc, next) => acc + next);
            } while (_context.Products.Any(p => p.Sku == sku));

            return sku;
        }
    }
}
