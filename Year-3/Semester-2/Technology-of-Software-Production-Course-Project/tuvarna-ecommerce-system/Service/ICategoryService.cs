using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ICategoryService
    {
        Task<CategoryReadDTO> AddCategoryAsync(CategoryCreateDTO categoryDto);

        Task<CategoryReadDTO> PatchCategoryAsync(CategoryPatchDTO categoryDto);
    }

}
