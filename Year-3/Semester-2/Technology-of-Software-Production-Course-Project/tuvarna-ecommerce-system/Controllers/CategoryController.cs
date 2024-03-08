using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;

namespace tuvarna_ecommerce_system.Controllers
{
    [Route("api/v1/categories")]
    [ApiController]
    public class CategoryController : ControllerBase
    {
        private readonly ICategoryService _categoryService;

        public CategoryController(ICategoryService categoryService)
        {
            _categoryService = categoryService;
        }

        [HttpPost("add")]
        public async Task<ActionResult<CategoryReadDTO>> CreateCategory([FromBody] CategoryCreateDTO categoryDto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var createdCategoryDto = await _categoryService.AddCategoryAsync(categoryDto);
                return CreatedAtAction(nameof(CreateCategory), new { id = createdCategoryDto.Id }, createdCategoryDto);
            }
            catch (CustomDuplicateKeyException ex)
            {
                return Conflict(new { message = ex.Message });
            }
            catch (Exception)
            {
                return StatusCode(500, new { message = "An unexpected error occurred. Please try again later." });
            }
        }
    }

}

