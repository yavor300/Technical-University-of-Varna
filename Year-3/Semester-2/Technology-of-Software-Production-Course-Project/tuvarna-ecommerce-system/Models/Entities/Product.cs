using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Product : BaseEntity
    {
        [Required]
        [StringLength(128)]
        public string Name { get; set; }

        [Required]
        [StringLength(1024)]
        public string Description { get; set; }

        [Required]
        [StringLength(256)]
        public string ShortDescription { get; set; }

        [Required]
        [Column(TypeName = "decimal(6,2)")]
        public decimal Price { get; set; }

        [Column(TypeName = "decimal(6,2)")]
        public decimal? DiscountPrice { get; set; }

        [Required]
        public string ImageUrl { get; set; }

        [Required]
        public int StockQuantity { get; set; }

        [Required]
        [StringLength(6)]
        public string Sku { get; set; }

        [Required]
        public ProductTypeEnum ProductType { get; set; }

        public int CategoryId { get; set; }

        public Category Category { get; set; } = null!;

        public ICollection<Tag> Tags { get; set; }

        public ICollection<ProductImage> AdditionalImages { get; set; } = new List<ProductImage>();
    }
}
