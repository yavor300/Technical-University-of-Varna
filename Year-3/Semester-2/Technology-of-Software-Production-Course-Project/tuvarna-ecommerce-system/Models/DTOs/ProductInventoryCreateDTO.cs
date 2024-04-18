using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductInventoryCreateDTO : IValidatableObject
    {
        [Required]
        [Range(0, Double.MaxValue, ErrorMessage = "Price must be a non-negative value.")]
        public decimal Price { get; set; }

        [Range(0, Double.MaxValue, ErrorMessage = "Discount price must be a non-negative value.")]
        public decimal? DiscountPrice { get; set; }

        [Required]
        [Range(1, Int32.MaxValue, ErrorMessage = "Stock quantity must be at least 1.")]
        public int StockQuantity { get; set; }

        public DateTime? ImportDate { get; set; }

        public int ProductId { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {

            if (ProductId <= 0)
            {
                yield return new ValidationResult($"The {nameof(ProductId)} field must be a positive number.", new[] { nameof(ProductId) });
            }
        }
    }

}
