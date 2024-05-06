using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Models.Entities.Enums;

namespace tuvarna_ecommerce_system.Data
{
    public class EcommerceDbContext : DbContext
    {
        public EcommerceDbContext(DbContextOptions<EcommerceDbContext> options) : base(options)
        {
        }

        public DbSet<Product> Products { get; set; }
        public DbSet<Tag> Tags { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<ProductInventory> ProductInventory { get; set; }
        public DbSet<ProductImage> ProductImages { get; set; }
        public DbSet<Sale> Sales { get; set; }
        public DbSet<SaleItem> SaleItems { get; set; }
        public DbSet<User> Users { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Product>()
                .HasMany(e => e.Tags)
                .WithMany(e => e.Products);

            modelBuilder.Entity<Category>()
                .HasMany(e => e.Products)
                .WithOne(e => e.Category)
                .HasForeignKey(e => e.CategoryId)
                .IsRequired(false);

            modelBuilder.Entity<Category>()
                .HasIndex(c => c.Name)
                .IsUnique();

            modelBuilder.Entity<Category>()
                .Property(c => c.Name)
                .IsRequired()
                .HasMaxLength(32);

            modelBuilder.Entity<Tag>()
                .HasIndex(t => t.Name)
                .IsUnique();

            modelBuilder.Entity<Product>()
                .HasMany(p => p.Inventories)
                .WithOne(i => i.Product)
                .HasForeignKey(i => i.ProductId);

            modelBuilder.Entity<Sale>()
            .HasMany(s => s.SaleItems)
            .WithOne(si => si.Sale)
            .HasForeignKey(si => si.SaleId);

            modelBuilder.Entity<SaleItem>()
                .HasOne(si => si.Product)
                .WithMany(p => p.SaleItems)
                .HasForeignKey(si => si.ProductId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<SaleItem>()
                .HasOne(si => si.Sale)
                .WithMany(s => s.SaleItems)
                .HasForeignKey(si => si.SaleId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Product>()
                .HasMany(p => p.AdditionalImages)
                .WithOne(i => i.Product)
                .HasForeignKey(i => i.ProductId);

            modelBuilder.Entity<User>()
                .ToTable("Users")
                .HasDiscriminator<RoleEnum>("Role")
                .HasValue<Administrator>(RoleEnum.ADMIN)
                .HasValue<Employee>(RoleEnum.EMPLOYEE)
                .HasValue<Customer>(RoleEnum.CUSTOMER);

            modelBuilder.Entity<Customer>()
                .HasMany(c => c.Sales)
                .WithOne(s => s.Customer)
                .HasForeignKey(s => s.CustomerId)
                .OnDelete(DeleteBehavior.Cascade);

            modelBuilder.Entity<Sale>()
    .HasOne(s => s.Customer)
    .WithMany(c => c.Sales)
    .HasForeignKey(s => s.CustomerId)
    .OnDelete(DeleteBehavior.Restrict); // or Use DeleteBehavior.Restrict for no action

            modelBuilder.Entity<Sale>()
    .HasOne(s => s.Employee)
    .WithMany(c => c.Sales)
    .HasForeignKey(s => s.EmployeeId)
    .OnDelete(DeleteBehavior.Restrict); // or Use DeleteBehavior.Restrict for no action

            modelBuilder.Entity<Tag>().HasQueryFilter(t => !t.IsDeleted);
            modelBuilder.Entity<Category>().HasQueryFilter(c => !c.IsDeleted);
            modelBuilder.Entity<Product>().HasQueryFilter(p => !p.IsDeleted);
            modelBuilder.Entity<ProductImage>().HasQueryFilter(pI => !pI.IsDeleted);
        }
    }
}
