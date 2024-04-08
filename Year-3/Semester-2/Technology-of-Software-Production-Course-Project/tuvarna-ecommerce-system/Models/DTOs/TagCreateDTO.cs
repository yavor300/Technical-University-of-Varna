using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class TagCreateDTO
    {

        [Required(ErrorMessage = "Name is required.")]
        [StringLength(32, ErrorMessage = "Name must be less than 32 characters.")]
        public string Name { get; set; }
    }
}
