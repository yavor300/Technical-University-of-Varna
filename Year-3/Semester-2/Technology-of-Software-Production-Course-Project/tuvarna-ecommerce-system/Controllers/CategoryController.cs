﻿using Microsoft.AspNetCore.Mvc;
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
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpPatch("edit")]
        public async Task<ActionResult<CategoryReadDTO>> PatchCategory([FromBody] CategoryPatchDTO categoryDto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var updatedCategoryDto = await _categoryService.PatchCategoryAsync(categoryDto);
                return Ok(updatedCategoryDto);
            }
            catch (EntityNotFoundException ex)
            {
                return NotFound(new { message = ex.Message });
            }
            catch (InvalidOperationException ex)
            {
                return Conflict(new { message = ex.Message });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpGet("get/id/{id}")]
        public async Task<ActionResult<CategoryReadDTO>> GetCategoryById([FromRoute] CategoryGetByIdDTO dto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var categoryDto = await _categoryService.GetCategoryByIdAsync(dto);
                return Ok(categoryDto);
            }
            catch (EntityNotFoundException ex)
            {
                return NotFound(new { message = ex.Message });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpGet("get/name/{name}")]
        public async Task<ActionResult<CategoryReadDTO>> GetCategoryByName([FromRoute] CategoryGetByNameDTO dto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var categoryDto = await _categoryService.GetCategoryByNameAsync(dto);
                return Ok(categoryDto);
            }
            catch (EntityNotFoundException ex)
            {
                return NotFound(new { message = ex.Message });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpGet]
        public async Task<ActionResult<CategoryReadAllDTO>> GetAllCategories()
        {
            try
            {
                var categoriesDto = await _categoryService.GetAllCategoriesAsync();
                return Ok(categoriesDto);
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

        [HttpDelete("delete/{id}")]
        public async Task<ActionResult<CategoryReadDTO>> Delete(int Id)
        {

            try
            {
                var deleted = await _categoryService.Delete(Id);
                return Ok(deleted);
            }
            catch (InvalidDataException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            catch (EntityNotFoundException ex)
            {
                return NotFound(new { message = ex.Message });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }
    }
}
