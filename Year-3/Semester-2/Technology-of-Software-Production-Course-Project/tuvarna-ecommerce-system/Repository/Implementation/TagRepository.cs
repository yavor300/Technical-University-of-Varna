using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Exceptions;
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

            var existing = await _context.Tags
                .IgnoreQueryFilters()
                .FirstOrDefaultAsync(t => t.Name.Equals(tag.Name.ToLowerInvariant()));

            if (existing != null)
            {
                if (existing.IsDeleted)
                {
                    existing.IsDeleted = false;
                    existing.Name = tag.Name.ToLowerInvariant();
                    await _context.SaveChangesAsync();
                    return existing;
                }
            }

            tag.Name = tag.Name.ToLowerInvariant();
            _context.Tags.Add(tag);
            await _context.SaveChangesAsync();
            return tag;
        }


        public async Task<List<Tag>> GetAllAsync()
        {
            return await _context.Tags.ToListAsync();
        }

        public async Task<Tag> GetByNameAsync(string name)
        {
            string normalizedName = name.ToLowerInvariant();
            var tag = await _context.Tags
                .FirstOrDefaultAsync(t => t.Name == normalizedName);

            if (tag == null)
            {
                throw new EntityNotFoundException($"Tag with name {name} not found.");
            }

            return tag;
        }

        public async Task<Tag> PatchAsync(int id, string name)
        {

            var tag = await _context.Tags.FindAsync(id);
            if (tag == null)
            {
                throw new EntityNotFoundException(id, "Tag");
            }

            if (!string.IsNullOrEmpty(name))
            {
                string normalizedName = name.ToLowerInvariant();

                var existingTag = await _context.Tags
                    .AsNoTracking()
                    .FirstOrDefaultAsync(t => t.Id != id && t.Name == normalizedName);

                if (existingTag != null)
                {
                    throw new InvalidOperationException($"A tag with the name {name} already exists.");
                }

                tag.Name = normalizedName;
            }

            await _context.SaveChangesAsync();
            return tag;
        }

        public async Task<Tag> Delete(int id)
        {
            var tag = await _context.Tags.FindAsync(id);
            if (tag == null)
            {
                throw new EntityNotFoundException(id, "Tag");
            }

            tag.IsDeleted = true;
            await _context.SaveChangesAsync();

            return tag;
        }
    }
}
