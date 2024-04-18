using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using tuvarna_ecommerce_system.Models.Entities.Base;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Sale : BaseEntity
    {

        [Required]
        public DateTime SaleDate { get; set; }

        public ICollection<SaleItem> SaleItems { get; set; } = new List<SaleItem>();

        //public int EmployeeId { get; set; }
        //[ForeignKey("EmployeeId")]
        //public Employee Employee { get; set; }
        //public int CustomerId { get; set; }
        //[ForeignKey("CustomerId")]
        //public Customer Customer { get; set; }
    }
}
