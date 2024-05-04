using Microsoft.AspNetCore.Mvc;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Service;
using tuvarna_ecommerce_system.Service.Implementation;

namespace tuvarna_ecommerce_system.Controllers
{
    [Route("api/v1/users")]
    [ApiController]
    public class UserController : ControllerBase
    {

        private readonly IUserService _userService;
        private readonly AuthService _authService;

        public UserController(IUserService userService, AuthService authService)
        {
            _userService = userService;
            _authService = authService;
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

        [HttpPost("login")]
        public async Task<IActionResult> Login([FromBody] UserLoginDTO userLoginDto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(new { message = "Validation failed", errors = ModelState.Values.SelectMany(v => v.Errors).Select(e => e.ErrorMessage) });
            }

            try
            {
                var userReadDto = await _userService.FindByEmailAndPassword(userLoginDto);
                if (userReadDto == null)
                {
                    return Unauthorized("Invalid credentials");
                }

                var token = _authService.GenerateJwtToken(userReadDto);

                return Ok(new { Token = token });
            }
            catch (InternalServerErrorException ex)
            {
                return StatusCode(500, new { message = ex.Message });
            }
        }

    }
}
