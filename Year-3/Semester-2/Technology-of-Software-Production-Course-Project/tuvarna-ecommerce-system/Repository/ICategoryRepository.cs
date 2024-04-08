using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Data.Repositories
{
    public interface ICategoryRepository
    {
        Task<Category> CreateAsync(Category category);

        Task<Category> PatchAsync(int id, string? name, string? description);

        Task<Category> GetByIdAsync(int id);

        Task<Category> GetByNameAsync(string name);

        Task<List<Category>> GetAllAsync();
    }
}
