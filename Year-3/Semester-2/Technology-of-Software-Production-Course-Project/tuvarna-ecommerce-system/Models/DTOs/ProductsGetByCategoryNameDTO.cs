using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductsGetByCategoryNameDTO
    {

        [Required(ErrorMessage = "Category name is required.")]
        [StringLength(128, ErrorMessage = "Category name must be less than 128 characters.")]
        public string CategoryName { get; set; }
    }
}
