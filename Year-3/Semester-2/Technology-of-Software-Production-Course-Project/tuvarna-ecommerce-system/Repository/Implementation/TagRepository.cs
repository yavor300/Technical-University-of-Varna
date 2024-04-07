using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class TagRepository : ITagRepository
    {

        private readonly EcommerceDbContext _context;

        public TagRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<Tag> CreateAsync(Tag tag)
        {

            tag.Name = tag.Name.ToLowerInvariant();
            _context.Tags.Add(tag);
            await _context.SaveChangesAsync();
            return tag;
        }
    }
}
