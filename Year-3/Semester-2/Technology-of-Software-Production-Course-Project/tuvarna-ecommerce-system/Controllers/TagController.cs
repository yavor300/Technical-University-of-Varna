﻿using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;

namespace tuvarna_ecommerce_system.Controllers
{

    [Route("api/v1/tags")]
    [ApiController]
    public class TagController : ControllerBase
    {

        private readonly ITagService _tagService;

        public TagController(ITagService tagService)
        {
            _tagService = tagService;
        }


        [HttpPost("add")]
        public async Task<ActionResult<TagReadDTO>> CreateTag([FromBody] TagCreateDTO dto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var createdTagDto = await _tagService.AddTagAsync(dto);
                return CreatedAtAction(nameof(CreateTag), new { id = createdTagDto.Id }, createdTagDto);
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
    }
}