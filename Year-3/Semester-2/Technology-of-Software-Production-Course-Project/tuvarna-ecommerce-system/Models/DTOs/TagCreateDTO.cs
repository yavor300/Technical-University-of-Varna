using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class TagCreateDTO
    {

        [Required]
        [StringLength(32)]
        public string Name { get; set; } = string.Empty;
    }
}
