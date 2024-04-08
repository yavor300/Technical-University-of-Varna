using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryCreateDTO
    {
        [Required(ErrorMessage = "Name is required.")]
        [StringLength(32, ErrorMessage = "Name must be less than 32 characters.")]
        public string Name { get; set; }

        [StringLength(128, ErrorMessage = "Description must be less than 128 characters.")]
        public string? Description { get; set; }

        [StringLength(256, ErrorMessage = "ImageUrl must be less than 256 characters.")]
        public string? ImageUrl { get; set; }
    }
}
