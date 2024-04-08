using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ICategoryService
    {
        Task<CategoryReadDTO> AddCategoryAsync(CategoryCreateDTO categoryDto);

        Task<CategoryReadDTO> PatchCategoryAsync(CategoryPatchDTO categoryDto);

        Task<CategoryReadDTO> GetCategoryByIdAsync(CategoryGetByIdDTO categoryDto);

        Task<CategoryReadDTO> GetCategoryByNameAsync(CategoryGetByNameDTO categoryDto);

        Task<CategoryReadAllDTO> GetAllCategoriesAsync();
    }
}
