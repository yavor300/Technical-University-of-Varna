using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface ITagRepository
    {

        Task<Tag> CreateAsync(Tag tag);
    }
}
