using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Tag : BaseEntity
    {
        [Required]
        [StringLength(32)]
        public required string Name { get; set; }
        public ICollection<Product> Products { get; set; } = [];
    }
}
