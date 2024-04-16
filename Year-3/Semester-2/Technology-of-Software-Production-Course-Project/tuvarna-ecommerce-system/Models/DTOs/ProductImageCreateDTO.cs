using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductImageCreateDTO
    {

        [Required(ErrorMessage = "ImageUrl is required.")]
        [StringLength(256, ErrorMessage = "ImageURL must be less than 256 characters.")]
        public required string ImageUrl { get; set; }

        public int ProductId;
    }
}
