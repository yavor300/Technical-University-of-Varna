namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Customer : User
    {
        public virtual ICollection<Sale> Sales { get; set; } = new List<Sale>();
    }
}
