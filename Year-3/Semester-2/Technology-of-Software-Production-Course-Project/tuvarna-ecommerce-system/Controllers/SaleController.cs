using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;

namespace tuvarna_ecommerce_system.Controllers
{

    [Route("api/v1/sales")]
    [ApiController]
    public class SaleController : ControllerBase
    {

        private readonly ISaleService _saleService;
        private readonly ISaleItemService _saleItemService;

        public SaleController(ISaleService saleService, ISaleItemService saleItemService)
        {
            _saleService = saleService;
            _saleItemService = saleItemService;
        }

        [HttpPost]
        public async Task<ActionResult<SaleReadDTO>> Create([FromBody] SaleCreateDTO dto)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", 
                    errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var createdSale = await _saleService.CreateAsync(dto.Date ?? DateTime.Now);
                List<SaleItemReadDTO> saleItems = await _saleItemService.CreateAsync(dto.Items, createdSale.Id);

                

                //foreach (var item in saleItems)
                //{
                //    await _saleItemService.AssociateWithSale(item.Id, createdSale.Id);
                //}

                createdSale.Items = saleItems;

                return Ok(createdSale);
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }
    }
}
