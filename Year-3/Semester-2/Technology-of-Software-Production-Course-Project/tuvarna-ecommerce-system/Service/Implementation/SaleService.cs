using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class SaleService : ISaleService
    {

        private readonly ISaleRepository _repository;
        private readonly ILogger<TagService> _logger;

        public SaleService(ISaleRepository repository, ILogger<TagService> logger)
        {
            _repository = repository;
            _logger = logger;
        }

        public async Task<SaleReadDTO> CreateAsync(DateTime dateTime)
        {
            try
            {

                var sale = new Sale
                {
                    SaleDate = dateTime
                };

                var createdSale = await _repository.CreateAsync(sale);

                var saleDto = new SaleReadDTO
                {
                    Id = createdSale.Id,
                    Date = createdSale.SaleDate
                };

                return saleDto;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Failed to create sale");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }
    }
}
