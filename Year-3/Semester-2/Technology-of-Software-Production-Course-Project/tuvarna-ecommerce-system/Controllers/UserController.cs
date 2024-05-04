using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;

namespace tuvarna_ecommerce_system.Controllers
{
    [Route("api/v1/users")]
    [ApiController]
    public class UserController : ControllerBase
    {

        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpPost("register")]
        public async Task<ActionResult<UserReadDTO>> Register([FromBody] UserCreateDTO userCreateDTO)
        {

            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var user = await _userService.CreateAsync(userCreateDTO);
                return CreatedAtAction(nameof(Register), new { id = user.Id }, user);
            }
            catch (ArgumentException ex)
            {
                return BadRequest(new { message = ex.Message });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }
    }
}
