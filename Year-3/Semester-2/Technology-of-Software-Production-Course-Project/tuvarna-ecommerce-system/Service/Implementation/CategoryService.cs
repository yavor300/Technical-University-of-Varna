using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data.Repositories;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Utils;

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
                Description = categoryDto.Description,
                ImageUrl = categoryDto.ImageUrl
            };

            try
            {
                var createdCategory = await _categoryRepository.CreateAsync(category);

                return new CategoryReadDTO
                {
                    Id = createdCategory.Id,
                    Name = createdCategory.Name,
                    Description = createdCategory.Description,
                    ImageUrl = createdCategory.ImageUrl
                };
            }
            catch (DbUpdateException ex)
            {
                ExceptionHandlerUtil.HandleDbUpdateException<CategoryService>(_logger, ex, categoryDto.Name, 
                    "Attempted to add a duplicate category: {EntityName}",
                    "Database exception occurred while adding a new category. {EntityName}"
                    );
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
                    Description = updatedCategory.Description,
                    ImageUrl = updatedCategory.ImageUrl
                };
            }
            catch (EntityNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (InvalidOperationException ex)
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
                    Description = category.Description,
                    ImageUrl = category.ImageUrl
                };
            }
            catch (EntityNotFoundException ex)
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
                    Description = category.Description,
                    ImageUrl = category.ImageUrl
                };
            }
            catch (EntityNotFoundException ex)
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

        public async Task<CategoryReadAllDTO> GetAllCategoriesAsync()
        {
            try
            {
                var categories = await _categoryRepository.GetAllAsync();

                var categoryDtos = categories.Select(category => new CategoryReadDTO
                {
                    Id = category.Id,
                    Name = category.Name,
                    Description = category.Description,
                    ImageUrl = category.ImageUrl
                }).ToList();

                var result = new CategoryReadAllDTO
                {
                    Categories = categoryDtos
                };

                return result;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<CategoryReadDTO> Delete(int Id)
        {

            if (Id == 0)
            {
                throw new InvalidDataException($"The {nameof(Id)} field is required.");
            }

            if (Id < 0)
            {
                throw new InvalidDataException($"The {nameof(Id)} field must be a positive number.");
            }

            try
            {

                var deleted = await _categoryRepository.Delete(Id);

                return new CategoryReadDTO
                {
                    Id = deleted.Id,
                    Name = deleted.Name,
                    Description = deleted.Description,
                    ImageUrl = deleted.ImageUrl
                };
            }
            catch (EntityNotFoundException ex)
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
    }
}
