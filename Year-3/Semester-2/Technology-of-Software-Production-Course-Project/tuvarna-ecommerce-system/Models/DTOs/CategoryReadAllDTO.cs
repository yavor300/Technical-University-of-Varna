namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryReadAllDTO
    {
        public List<CategoryReadDTO> Categories { get; set; } = new List<CategoryReadDTO>();
    }
}
