using System.ComponentModel.DataAnnotations;

namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class TagReadDTO
    {

        public int Id { get; set; }

        public string Name { get; set; } = string.Empty;
    }
}
