using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface ITagRepository
    {

        Task<Tag> CreateAsync(Tag tag);

        Task<Tag> PatchAsync(int id, string name);

        Task<List<Tag>> GetAllAsync();

        Task<Tag> GetByNameAsync(string name);

        Task<Tag> Delete(int id);
    }
}
