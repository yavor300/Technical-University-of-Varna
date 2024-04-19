namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleItemReadDTO
    {

        public int Id { get; set; }
        public int QuantitySold { get; set; }
        public int ProductId { get; set; }
        public string ProductName { get; set; }
        public decimal ProductPrice { get; set; }

        public decimal TotalPrice { get; set; }
    }
}
