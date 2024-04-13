using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface IProductRepository
    {

        Task<Product> CreateAsync(Product category, List<Tag> tags);

        Task<Product> GetByIdAsync(int id);

        Task<Product> PatchAsync(Product updated);

        Task<List<Product>> GetByCategoryId(int Id);
    }
}
