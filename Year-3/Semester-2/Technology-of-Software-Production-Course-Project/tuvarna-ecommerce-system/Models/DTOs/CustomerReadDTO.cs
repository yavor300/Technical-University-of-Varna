namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CustomerReadDTO : UserReadDTO
    {
        public ICollection<SaleReadDTO> Sales { get; set; } = new List<SaleReadDTO>();
    }
}
