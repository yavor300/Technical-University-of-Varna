using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductPatchDTO
    {

        public int Id { get; set; }

        [StringLength(128, ErrorMessage = "Name must be less than 128 characters.")]
        public string? Name { get; set; }

        [StringLength(1024, ErrorMessage = "Description must be less than 1024 characters.")]
        public string? Description { get; set; }

        [StringLength(256, ErrorMessage = "Short description must be less than 256 characters.")]
        public string? ShortDescription { get; set; }

        public string? ImageUrl { get; set; }

        public string? ProductType { get; set; }
        public string? CategoryName { get; set; }
        public Boolean? isFeatured { get; set; }
        public List<TagCreateDTO>? Tags { get; set; }

        public List<ProductImageCreateDTO>? Images { get; set; }

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {

            if (Id <= 0)
            {
                yield return new ValidationResult($"The {nameof(Id)} field must be a positive number.", new[] { nameof(Id) });
            }

            if (Tags != null)
            {
                var duplicateTags = Tags.GroupBy(t => t.Name).Where(g => g.Count() > 1).Select(g => g.Key);
                foreach (var duplicateTag in duplicateTags)
                {
                    yield return new ValidationResult($"Duplicate tag: {duplicateTag}", new[] { nameof(Tags) });
                }
            }
        }
    }
}
