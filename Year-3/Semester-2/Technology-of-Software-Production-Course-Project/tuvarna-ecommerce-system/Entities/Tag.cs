using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Entities
{
    public class Tag : BaseEntity
    {
        [Required]
        [StringLength(32)]
        public string Name { get; set; }
        public ICollection<Product> Products { get; set; }
    }
}

