using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class SaleItem : BaseEntity
    {

        [Required]
        public int QuantitySold { get; set; }

        public int ProductId { get; set; }
        public Product Product { get; set; } = null!;

        public int SaleId { get; set; }
        public Sale Sale { get; set; } = null!;
    }
}
