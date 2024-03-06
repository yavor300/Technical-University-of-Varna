using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Entities;

namespace tuvarna_ecommerce_system.Data
{
    public class EcommerceDbContext : DbContext
    {
        public EcommerceDbContext(DbContextOptions<EcommerceDbContext> options) : base(options)
        {
        }
        public DbSet<Product> Products { get; set; }
    }
}
