using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class UserCreateDTO
    {

        [Required(ErrorMessage = "Username is required.")]
        [StringLength(50, ErrorMessage = "Username must not exceed 50 characters.")]
        public string Username { get; set; }

        [Required(ErrorMessage = "Email is required.")]
        [EmailAddress(ErrorMessage = "A valid email address is required.")]
        [StringLength(100, ErrorMessage = "Email must not exceed 100 characters.")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Password is required.")]
        [StringLength(100, MinimumLength = 6, ErrorMessage = "Password must be between 6 and 100 characters long.")]
        public string Password { get; set; }

        [Required(ErrorMessage = "Role is required.")]
        public string Role { get; set; }
    }
}
