using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class TagPatchDTO : IValidatableObject
    {

        public int Id { get; set; }

        [Required(ErrorMessage = "Name is required.")]
        [StringLength(32, ErrorMessage = "Name must be less than 32 characters.")]
        public string Name { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (Id == 0)
            {
                yield return new ValidationResult($"The {nameof(Id)} field is required.", new[] { nameof(Id) });
            }

            if (Id < 0)
            {
                yield return new ValidationResult($"The {nameof(Id)} field must be a positive number.", new[] { nameof(Id) });
            }
        }
    }
}
