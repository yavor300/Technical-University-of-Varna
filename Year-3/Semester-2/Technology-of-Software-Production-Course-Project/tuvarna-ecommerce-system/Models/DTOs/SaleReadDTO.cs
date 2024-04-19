namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleReadDTO
    {

        public int Id { get; set; }

        public DateTime Date { get; set; }

        public List<SaleItemReadDTO> Items { get; set; } = new List<SaleItemReadDTO>();

    }
}
