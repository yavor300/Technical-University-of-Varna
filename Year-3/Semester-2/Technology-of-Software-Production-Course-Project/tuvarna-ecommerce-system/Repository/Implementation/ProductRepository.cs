using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Exceptions;
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
            product.Tags = productTags;
            _context.Products.Add(product);
            await _context.SaveChangesAsync();

            return product;
        }

        public async Task<Product> GetByIdAsync(int id)
        {

            var product = await _context.Products
                .Include(p => p.Tags)
                .Include(p => p.Category)
                .Include(p => p.AdditionalImages)
                .Include(p => p.Inventories)
                .FirstOrDefaultAsync(p => p.Id == id);

            if (product == null)
            {
                throw new EntityNotFoundException(id, "Product");
            }

            return product;
        }

        public async Task<Product> PatchAsync(Product updatedProduct)
        {

            var existingProduct = await GetByIdAsync(updatedProduct.Id);

            existingProduct.Name = updatedProduct.Name;
            existingProduct.Description = updatedProduct.Description;
            existingProduct.ShortDescription = updatedProduct.ShortDescription;
            existingProduct.ImageUrl = updatedProduct.ImageUrl;
            existingProduct.ProductType = updatedProduct.ProductType;
            existingProduct.CategoryId = updatedProduct.CategoryId;

            if (updatedProduct.Tags != null && updatedProduct.Tags.Count > 0)
            {
                existingProduct.Tags.Clear();
                foreach (var tag in updatedProduct.Tags)
                {
                    var existingTag = await _context.Tags.FindAsync(tag.Id);
                    if (existingTag != null)
                    {
                        _context.Tags.Attach(existingTag);
                        existingProduct.Tags.Add(existingTag);
                    }
                }
            }

            await _context.SaveChangesAsync();

            return existingProduct;
        }

        public async Task<List<Product>> GetByCategoryName(string name)
        {

            return await _context.Products
                .Where(p => p.Category.Name == name && !p.IsDeleted)
                .Include(p => p.Category)
                .Include(p => p.Tags)
                .Include(p => p.AdditionalImages)
                .ToListAsync();
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
