using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductCreateDTO : IValidatableObject
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

        [Required(ErrorMessage = "Category name is required.")]
        public string CategoryName { get; set; }

        public List<TagCreateDTO> Tags { get; set; } = new List<TagCreateDTO>();

        public IEnumerable<ValidationResult> Validate(ValidationContext validationContext)
        {
            var duplicateTags = Tags.GroupBy(t => t.Name).Where(g => g.Count() > 1).Select(g => g.Key);
            foreach (var duplicateTag in duplicateTags)
            {
                yield return new ValidationResult($"Duplicate tag: {duplicateTag}", new[] { nameof(Tags) });
            }
        }
    }
}
