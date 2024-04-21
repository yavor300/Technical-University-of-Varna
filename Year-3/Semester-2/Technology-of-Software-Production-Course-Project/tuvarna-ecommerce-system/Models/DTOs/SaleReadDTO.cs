namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class SaleReadDTO
    {

        public int Id { get; set; }
        public DateTime Date { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public string? CompanyName { get; set; }
        public string Country { get; set; }
        public string StreetAddress { get; set; }
        public string Town { get; set; }
        public string State { get; set; }
        public string ZipCode { get; set; }
        public string Email { get; set; }
        public string PhoneNumber { get; set; }
        public string? OrderNotes { get; set; }
        public List<SaleItemReadDTO> Items { get; set; } = new List<SaleItemReadDTO>();

    }
}
