using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data.Repositories;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class CategoryService : ICategoryService
    {
        private readonly ICategoryRepository _categoryRepository;
        private readonly ILogger<CategoryService> _logger;

        public CategoryService(ICategoryRepository categoryRepository, ILogger<CategoryService> logger)
        {
            _categoryRepository = categoryRepository;
            _logger = logger;
        }

        public async Task<CategoryReadDTO> AddCategoryAsync(CategoryCreateDTO categoryDto)
        {
            var category = new Category
            {
                Name = categoryDto.Name,
                Description = categoryDto.Description
            };

            try
            {
                var createdCategory = await _categoryRepository.CreateAsync(category);

                return new CategoryReadDTO
                {
                    Id = createdCategory.Id,
                    Name = createdCategory.Name,
                    Description = createdCategory.Description
                };
            }
            catch (DbUpdateException ex)
            {
                HandleDbUpdateException(ex, categoryDto);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<CategoryReadDTO> PatchCategoryAsync(CategoryPatchDTO categoryDto)
        {
            try
            {
                var updatedCategory = await _categoryRepository.PatchAsync(categoryDto.Id, categoryDto.Name, categoryDto.Description);

                return new CategoryReadDTO
                {
                    Id = updatedCategory.Id,
                    Name = updatedCategory.Name,
                    Description = updatedCategory.Description
                };
            }
            catch (CategoryNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<CategoryReadDTO> GetCategoryByIdAsync(CategoryGetByIdDTO categoryGetByIdDto)
        {
            try
            {
                var category = await _categoryRepository.GetByIdAsync(categoryGetByIdDto.Id);

                return new CategoryReadDTO
                {
                    Id = category.Id,
                    Name = category.Name,
                    Description = category.Description
                };
            }
            catch (CategoryNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<CategoryReadDTO> GetCategoryByNameAsync(CategoryGetByNameDTO categoryDto)
        {
            try
            {
                var category = await _categoryRepository.GetByNameAsync(categoryDto.Name);

                return new CategoryReadDTO
                {
                    Id = category.Id,
                    Name = category.Name,
                    Description = category.Description
                };
            }
            catch (CategoryNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        private void HandleDbUpdateException(DbUpdateException ex, CategoryCreateDTO categoryDto)
        {
            if (ex.InnerException is SqlException sqlEx && (sqlEx.Number == 2601 || sqlEx.Number == 2627))
            {
                _logger.LogError(ex, "Attempted to add a duplicate category: {CategoryName}", categoryDto.Name);
                throw new CustomDuplicateKeyException($"A category with the name {categoryDto.Name} already exists.", ex);
            }

            _logger.LogError(ex, "Database exception occurred while adding a new category.");
        }
    }
}
