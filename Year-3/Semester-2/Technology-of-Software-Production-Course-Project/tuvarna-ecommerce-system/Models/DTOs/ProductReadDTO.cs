namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class ProductReadDTO
    {

        public int Id { get; set; }
        public string Name { get; set; }
        public string Sku { get; set; }
        public string Description { get; set; }
        public string ShortDescription { get; set; }
        public string ImageUrl { get; set; }
        public string ProductType { get; set; }
        public CategoryReadDTO? Category { get; set; }
        public bool IsFeatured { get; set; }
        public List<TagReadDTO> Tags { get; set; } = new List<TagReadDTO>();
        public List<ProductImageReadDTO> Images { get; set; } = new List<ProductImageReadDTO>();
        public List<ProductInventoryReadDTO> Inventories { get; set; } = new List<ProductInventoryReadDTO>();

        public List<SaleItemReadDTO> Sales { get; set; } = new List<SaleItemReadDTO>();
    }
}
