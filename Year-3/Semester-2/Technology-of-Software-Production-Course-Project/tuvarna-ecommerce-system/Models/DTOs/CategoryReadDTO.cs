using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryReadDTO
    {
        public int Id { get; set; }
        [Required]
        [StringLength(32)]
        public string Name { get; set; } = string.Empty;

        [StringLength(128)]
        public string? Description { get; set; }
    }
}
