using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Data.Repositories;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class CategoryRepository : ICategoryRepository
    {
        private readonly EcommerceDbContext _context;

        public CategoryRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<Category> CreateAsync(Category newCategory)
        {
            var existingCategory = await _context.Categories
                .IgnoreQueryFilters()
                .FirstOrDefaultAsync(c => c.Name.Equals(newCategory.Name.ToLowerInvariant()));

            if (existingCategory != null)
            {
                if (existingCategory.IsDeleted)
                {
                    existingCategory.IsDeleted = false;
                    existingCategory.Description = newCategory.Description;
                    existingCategory.ImageUrl = newCategory.ImageUrl;
                    await _context.SaveChangesAsync();
                    return existingCategory;
                }
            }

            newCategory.Name = newCategory.Name.ToLowerInvariant();
            _context.Categories.Add(newCategory);
            await _context.SaveChangesAsync();
            return newCategory;
        }


        public async Task<Category> PatchAsync(int id, string? name, string? description)
        {

            var category = await _context.Categories.FindAsync(id);
            if (category == null)
            {
                throw new EntityNotFoundException(id, "Category");
            }

            if (!string.IsNullOrEmpty(name))
            {
                string normalizedName = name.ToLowerInvariant();

                var existingCategory = await _context.Categories
                    .AsNoTracking()
                    .FirstOrDefaultAsync(c => c.Id != id && c.Name == normalizedName);

                if (existingCategory != null)
                {
                    throw new InvalidOperationException($"A category with the name {name} already exists.");
                }

                category.Name = normalizedName;
            }

            if (!string.IsNullOrEmpty(description))
            {
                category.Description = description;
            }
 
            await _context.SaveChangesAsync();
            return category;
        }

        public async Task<Category> GetByIdAsync(int id)
        {

            var category = await _context.Categories.FindAsync(id);
            if (category == null)
            {
                throw new EntityNotFoundException(id, "Category");
            }

            return category;
        }

        public async Task<Category> GetByNameAsync(string name)
        {

            string normalizedName = name.ToLowerInvariant();
            var category = await _context.Categories
                .Include(c => c.Products)
                .FirstOrDefaultAsync(c => c.Name == normalizedName);

            if (category == null)
            {
                throw new EntityNotFoundException($"Category with name {name} not found.");
            }

            return category;
        }

        public async Task<List<Category>> GetAllAsync()
        {
            return await _context.Categories.ToListAsync();
        }

        public async Task<Category> Delete(int id)
        {
            var category = await _context.Categories.FindAsync(id);
            if (category == null)
            {
                throw new EntityNotFoundException(id, "Category");
            }

            category.IsDeleted = true;
            await _context.SaveChangesAsync();

            return category;
        }
    }
}
