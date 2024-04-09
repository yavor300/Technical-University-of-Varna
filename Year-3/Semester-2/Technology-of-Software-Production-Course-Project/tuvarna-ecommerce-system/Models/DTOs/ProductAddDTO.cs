using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductAddDTO : IValidatableObject
    {

        [Required(ErrorMessage = "Name is required.")]
        [StringLength(128, ErrorMessage = "Name must be less than 128 characters.")]
        public string Name { get; set; }

        [Required(ErrorMessage = "Description is required.")]
        [StringLength(1024, ErrorMessage = "Description must be less than 1024 characters.")]
        public string Description { get; set; }

        [Required(ErrorMessage = "Short description is required.")]
        [StringLength(256, ErrorMessage = "Short description must be less than 256 characters.")]
        public string ShortDescription { get; set; }

        [Required(ErrorMessage = "Image url is required.")]
        public string ImageUrl { get; set; }

        [Required(ErrorMessage = "Product type is required.")]
        public string ProductType { get; set; }

        public int CategoryId { get; set; }

        public List<int> TagIds { get; set; } = new List<int>();

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (CategoryId == 0)
            {
                yield return new ValidationResult($"The {nameof(CategoryId)} field is required.", new[] { nameof(CategoryId) });
            }

            if (CategoryId < 0)
            {
                yield return new ValidationResult($"The {nameof(CategoryId)} field must be a positive number.", new[] { nameof(CategoryId) });
            }
        }
    }
}
