using System.ComponentModel.DataAnnotations.Schema;

namespace tuvarna_ecommerce_system.Models.Entities
{
    [Table("Employees")]
    public class Employee : User
    {
        public ICollection<Sale> Sales { get; set; } = new List<Sale>();
    }
}
