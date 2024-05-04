using System.ComponentModel.DataAnnotations.Schema;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Customer : User
    {
        public ICollection<Sale> Sales { get; set; } = new List<Sale>();
    }
}
