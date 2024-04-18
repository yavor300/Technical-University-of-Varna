using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface ISaleRepository
    {

        Task<Sale> CreateAsync(Sale sale);
    }
}
