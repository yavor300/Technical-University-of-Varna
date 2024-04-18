using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class SaleRepository : ISaleRepository
    {

        private readonly EcommerceDbContext _context;

        public SaleRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<Sale> CreateAsync(Sale sale)
        {

            _context.Sales.Add(sale);
            await _context.SaveChangesAsync();
            return sale;
        }
    }
}
