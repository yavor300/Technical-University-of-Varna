using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryGetByIdDTO
    {
        [Required]
        public int Id { get; set; }
    }
}
