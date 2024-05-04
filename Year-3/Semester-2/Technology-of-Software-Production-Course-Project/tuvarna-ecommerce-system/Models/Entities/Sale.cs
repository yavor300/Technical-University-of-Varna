using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;
using tuvarna_ecommerce_system.Models.Entities.Enums;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Sale : BaseEntity
    {

        [Required]
        public DateTime SaleDate { get; set; }

        [Required]
        [StringLength(50)]
        public string FirstName { get; set; }

        [Required]
        [StringLength(50)]
        public string LastName { get; set; }

        [StringLength(100)]
        public string? CompanyName { get; set; }

        [Required]
        [StringLength(100)]
        public string Country { get; set; }

        [Required]
        [StringLength(100)]
        public string StreetAddress { get; set; }

        [Required]
        [StringLength(50)]
        public string Town { get; set; }

        [StringLength(50)]
        public string State { get; set; }

        [Required]
        [StringLength(20)]
        public string ZipCode { get; set; }

        [Required]
        [StringLength(100)]
        public string Email { get; set; }

        [Required]
        [StringLength(20)]
        public string PhoneNumber { get; set; }

        [StringLength(500)]
        public string? OrderNotes { get; set; }

        [Range(0, 100, ErrorMessage = "Discount must be between 0% and 100%.")]

        public int? DiscountPercentage { get; set; }

        [Required]
        public PaymentTypeEnum PaymentType { get; set; }

        [Required]
        public ShippingTypeEnum ShippingType { get; set; }

        public ICollection<SaleItem> SaleItems { get; set; } = new List<SaleItem>();

        //public int EmployeeId { get; set; }
        //[ForeignKey("EmployeeId")]
        //public Employee Employee { get; set; }
        //public int CustomerId { get; set; }
        //[ForeignKey("CustomerId")]
        //public Customer Customer { get; set; }
    }
}
