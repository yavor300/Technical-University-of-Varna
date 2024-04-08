using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryGetByIdDTO : IValidatableObject
    {

        public int Id { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            if (Id <= 0)
            {
                yield return new ValidationResult($"The {nameof(Id)} path variable must be a positive number.", new[] { nameof(Id) });
            }
        }
    }
}
