using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class SaleItemRepository : ISaleItemRepository
    {

        private readonly EcommerceDbContext _context;

        public SaleItemRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<SaleItem> AssociateWithSale(int SoldItemId, int SaleId)
        {

            var soldItem = await _context.SaleItems.FindAsync(SoldItemId);
            soldItem.SaleId = SaleId;
            if (soldItem == null)
            {
                throw new EntityNotFoundException(SoldItemId, "Sold item");
            }

            await _context.SaveChangesAsync();
            return soldItem;
        }

        public async Task<SaleItem> CreateAsync(SaleItem saleItem)
        {

            _context.SaleItems.Add(saleItem);
            await _context.SaveChangesAsync();
            return saleItem;
        }
    }
}
