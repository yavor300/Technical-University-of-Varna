using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Category : BaseEntity
    {
        [Required]
        [StringLength(32)]
        public string Name { get; set; } = string.Empty;

        [StringLength(128)]
        public string? Description { get; set; }

        public ICollection<Product> Products { get; set; } = new List<Product>();
    }
}
