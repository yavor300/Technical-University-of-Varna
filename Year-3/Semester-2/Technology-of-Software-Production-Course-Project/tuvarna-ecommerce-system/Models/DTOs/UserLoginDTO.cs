using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class UserLoginDTO
    {
        [Required(ErrorMessage = "Email is required.")]
        [EmailAddress(ErrorMessage = "A valid email address is required.")]
        [StringLength(100, ErrorMessage = "Email must not exceed 100 characters.")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Password is required.")]
        public string Password { get; set; }
    }
}
