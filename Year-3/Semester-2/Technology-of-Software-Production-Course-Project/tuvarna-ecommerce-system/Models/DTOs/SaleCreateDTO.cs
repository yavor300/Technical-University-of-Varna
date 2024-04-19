using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleCreateDTO : IValidatableObject
    {

        public DateTime? Date { get; set; }

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
