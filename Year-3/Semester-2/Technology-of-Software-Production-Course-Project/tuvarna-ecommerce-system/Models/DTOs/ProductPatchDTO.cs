using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductPatchDTO : ProductCreateDTO
    {

        public int Id { get; set; }

        public override IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            // First, call base class validation to include its validation logic
            foreach (var validationResult in base.Validate(validationContext))
            {
                yield return validationResult;
            }

            // Additional validation for the Id field
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
