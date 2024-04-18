using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;

namespace tuvarna_ecommerce_system.Controllers
{

    [Route("api/v1/inventory")]
    [ApiController]
    public class ProductInventoryController : ControllerBase
    {

        private readonly IProductInventoryService _service;

        public ProductInventoryController(IProductInventoryService service)
        {
            _service = service;
        }

        [HttpPost]
        public async Task<ActionResult<ProductInventoryReadDTO>> Create([FromBody] ProductInventoryCreateDTO dto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var created = await _service.CreateAsync(dto);
                return CreatedAtAction(nameof(Create), new { id = created.Id }, created);
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
