namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleReadDTO
    {

        public int Id { get; set; }

        public DateTime SaleDate { get; set; }

        public List<SaleItemReadDTO> SaleItems { get; set; } = new List<SaleItemReadDTO>();

    }
}
