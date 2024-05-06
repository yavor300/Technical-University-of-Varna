using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;
using tuvarna_ecommerce_system.Models.Entities.Enums;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public abstract class User : BaseEntity
    {

        [Required]
        [StringLength(50)]
        public string Username { get; set; }

        [Required]
        public string Password { get; set; }

        [Required]
        [EmailAddress]
        [StringLength(100)]
        public string Email { get; set; }

        public RoleEnum Role { get; set; }
    }
}
