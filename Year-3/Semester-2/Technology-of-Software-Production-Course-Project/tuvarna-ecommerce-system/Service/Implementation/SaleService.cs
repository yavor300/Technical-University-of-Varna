using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Models.Entities.Enums;
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

        public async Task<SaleReadDTO> CreateAsync(SaleCreateDTO dto)
        {

            try
            {
                var paymentType = Enum.Parse<PaymentTypeEnum>(dto.PaymentType, true);
                var shippingType = Enum.Parse<ShippingTypeEnum>(dto.ShippingType, true);
                var toCreate = new Sale
                {
                    SaleDate = dto.Date ?? DateTime.Now,
                    FirstName = dto.FirstName,
                    LastName = dto.LastName,
                    CompanyName = dto.CompanyName,
                    Country = dto.Country,
                    StreetAddress = dto.StreetAddress,
                    Town = dto.Town,
                    State = dto.State,
                    ZipCode = dto.ZipCode,
                    Email = dto.Email,
                    PhoneNumber = dto.PhoneNumber,
                    DiscountPercentage = dto.DiscountPercentage,
                    PaymentType = paymentType,
                    ShippingType = shippingType,
                    OrderNotes = dto.OrderNotes,
                };
                var createdSale = await _repository.CreateAsync(toCreate);
                var saleDto = new SaleReadDTO
                {
                    Id = createdSale.Id,
                    Date = createdSale.SaleDate,
                    FirstName = createdSale.FirstName,
                    LastName = createdSale.LastName,
                    CompanyName = createdSale.CompanyName,
                    Country = createdSale.Country,
                    StreetAddress = createdSale.StreetAddress,
                    Town = createdSale.Town,
                    State = createdSale.State,
                    ZipCode = createdSale.ZipCode,
                    Email = createdSale.Email,
                    PhoneNumber = createdSale.PhoneNumber,
                    DiscountPercentage = createdSale.DiscountPercentage,
                    PaymentType = createdSale.PaymentType.ToString(),
                    ShippingType = createdSale.ShippingType.ToString(),
                    OrderNotes = createdSale.OrderNotes,
                };

                return saleDto;
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Failed to create sale");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }
    }
}
