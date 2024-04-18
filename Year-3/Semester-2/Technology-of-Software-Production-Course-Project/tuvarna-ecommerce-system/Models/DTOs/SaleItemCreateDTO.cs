using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleItemCreateDTO
    {

        [Required(ErrorMessage = "Quantity sold is required.")]
        [Range(1, int.MaxValue, ErrorMessage = "Quantity sold must be at least 1.")]
        public int QuantitySold { get; set; }

        [Required(ErrorMessage = "Product ID is required.")]
        [Range(1, int.MaxValue, ErrorMessage = "Product ID must be a positive number.")]
        public int ProductId { get; set; }
    }
}
