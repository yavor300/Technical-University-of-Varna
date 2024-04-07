using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryPatchDTO
    {
        [Required]
        public int Id { get; set; }

        [StringLength(32)]
        public string? Name { get; set; }

        [StringLength(128)]
        public string? Description { get; set; }

        [StringLength(256)]
        public string? ImageUrl { get; set; }
    }
}
