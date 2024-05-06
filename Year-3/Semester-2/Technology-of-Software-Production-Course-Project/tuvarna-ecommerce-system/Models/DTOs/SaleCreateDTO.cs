using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleCreateDTO : IValidatableObject
    {

        public DateTime? Date { get; set; }

        [Required(ErrorMessage = "First name is required.")]
        [StringLength(50, ErrorMessage = "First name must not exceed 50 characters.")]
        public string FirstName { get; set; }

        [Required(ErrorMessage = "Last name is required.")]
        [StringLength(50, ErrorMessage = "Last name must not exceed 50 characters.")]
        public string LastName { get; set; }

        [StringLength(100, ErrorMessage = "Company name must not exceed 100 characters.")]
        public string? CompanyName { get; set; }

        [Required(ErrorMessage = "Country is required.")]
        [StringLength(100, ErrorMessage = "Country name must not exceed 100 characters.")]
        public string Country { get; set; }

        [Required(ErrorMessage = "Street address is required.")]
        [StringLength(100, ErrorMessage = "Street address must not exceed 100 characters.")]
        public string StreetAddress { get; set; }

        [Required(ErrorMessage = "Town is required.")]
        [StringLength(50, ErrorMessage = "Town name must not exceed 50 characters.")]
        public string Town { get; set; }

        [StringLength(50, ErrorMessage = "State name must not exceed 50 characters.")]
        public string State { get; set; }

        [Required(ErrorMessage = "Zip code is required.")]
        [StringLength(20, ErrorMessage = "Zip code must not exceed 20 characters.")]
        public string ZipCode { get; set; }

        [Required(ErrorMessage = "Email is required.")]
        [EmailAddress(ErrorMessage = "A valid email is required.")]
        [StringLength(100, ErrorMessage = "Email must not exceed 100 characters.")]
        public string Email { get; set; }

        [Required(ErrorMessage = "Phone number is required.")]
        [Phone(ErrorMessage = "A valid phone number is required.")]
        [StringLength(13, ErrorMessage = "Phone number must not exceed 13 characters.")]
        public string PhoneNumber { get; set; }

        [StringLength(500, ErrorMessage = "Order notes must not exceed 500 characters.")]
        public string? OrderNotes { get; set; }

        [Range(0, 100, ErrorMessage = "Discount must be between 0% and 100%.")]
        public int? DiscountPercentage { get; set; }

        [Required(ErrorMessage = "Payment type is required.")]
        public string PaymentType { get; set; }

        [Required(ErrorMessage = "Shipping type is required.")]
        public string ShippingType { get; set; }

        [Required(ErrorMessage = "CustomerEmail is required.")]

        public string CustomerEmail { get; set; }

        public List<SaleItemCreateDTO> Items { get; set; } = new List<SaleItemCreateDTO>();

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (Items == null || !Items.Any())
            {
                yield return new ValidationResult(
                    "At least one item must be included in the sale.",
                    [nameof(Items)]);
            }
        }
    }
}
