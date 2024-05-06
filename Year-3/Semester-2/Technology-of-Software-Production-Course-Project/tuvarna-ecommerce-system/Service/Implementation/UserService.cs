using Microsoft.AspNetCore.Identity;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Models.Entities.Enums;
using tuvarna_ecommerce_system.Repository;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class UserService : IUserService
    {

        private readonly IUserRepository _userRepository;
        private readonly IPasswordHasher<User> _passwordHasher;
        private readonly ILogger<UserService> _logger;

        public UserService(IUserRepository userRepository, IPasswordHasher<User> passwordHasher, ILogger<UserService> logger)
        {
            _userRepository = userRepository;
            _passwordHasher = passwordHasher;
            _logger = logger;
        }

        public async Task<UserReadDTO> CreateAsync(UserCreateDTO userCreateDTO)
        {
            try
            {
                var existingUserByEmail = await _userRepository.FindByEmail(userCreateDTO.Email);
                if (existingUserByEmail != null)
                {
                    throw new ArgumentException("A user with the same email already exists.");
                }

                var existingUserByUsername = await _userRepository.FindByUsernameAsync(userCreateDTO.Username);
                if (existingUserByUsername != null)
                {
                    throw new ArgumentException("A user with the same username already exists.");
                }

                User newUser;
                switch (Enum.Parse<RoleEnum>(userCreateDTO.Role, true))
                {
                    case RoleEnum.ADMIN:
                        newUser = new Administrator();
                        break;
                    case RoleEnum.CUSTOMER:
                        newUser = new Customer();
                        break;
                    case RoleEnum.EMPLOYEE:
                        newUser = new Employee();
                        break;
                    default:
                        throw new ArgumentException("Invalid role specified");
                }

                newUser.Username = userCreateDTO.Username;
                newUser.Email = userCreateDTO.Email;
                newUser.Password = _passwordHasher.HashPassword(newUser, userCreateDTO.Password);

                var createdUser = await _userRepository.CreateAsync(newUser);

                return new UserReadDTO
                {
                    Id = createdUser.Id,
                    Username = createdUser.Username,
                    Email = createdUser.Email,
                    Role = createdUser.Role.ToString()
                };
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<UserReadDTO> FindByEmailAndPassword(UserLoginDTO userLoginDTO)
        {

            try
            {
                var user = await _userRepository.FindByEmail(userLoginDTO.Email);

                if (user == null)
                {
                    _logger.LogInformation($"Authentication failed for user: {userLoginDTO.Email}. User not found.");
                    return null;
                }

                var verificationResult = _passwordHasher.VerifyHashedPassword(user, user.Password, userLoginDTO.Password);

                if (verificationResult == PasswordVerificationResult.Failed)
                {
                    _logger.LogInformation($"Authentication failed for user: {userLoginDTO.Email}. Incorrect password.");
                    return null;
                }

                return new UserReadDTO
                {
                    Id = user.Id,
                    Username = user.Username,
                    Email = user.Email,
                    Role = user.Role.ToString()
                };
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Failed to authenticate user.");
                throw new InternalServerErrorException("An error occurred while authenticating the user.", ex);
            }
        }

        public async Task<CustomerReadDTO> FindCustomerByUsername(string username)
        {
            try
            {
                var customer = await _userRepository.FindCustomerByUsername(username);
                if (customer == null)
                {
                    throw new ArgumentException($"No customer found with username: {username}");
                }

                var customerDto = new CustomerReadDTO
                {
                    Id = customer.Id,
                    Username = customer.Username,
                    Email = customer.Email,
                    Role = customer.Role.ToString(),
                    Sales = customer.Sales.Select(s => new SaleReadDTO
                    {
                        Id = s.Id,
                        Date = s.SaleDate,
                        FirstName = s.FirstName,
                        LastName = s.LastName,
                        CompanyName = s.CompanyName,
                        Country = s.Country,
                        StreetAddress = s.StreetAddress,
                        Town = s.Town,
                        State = s.State,
                        ZipCode = s.ZipCode,
                        Email = s.Email,
                        PhoneNumber = s.PhoneNumber,
                        DiscountPercentage = s.DiscountPercentage,
                        PaymentType = s.PaymentType.ToString(),
                        ShippingType = s.ShippingType.ToString(),
                        OrderNotes = s.OrderNotes,
                        Items = s.SaleItems.Select(si => new SaleItemReadDTO
                        {
                            Id = si.Id,
                            QuantitySold = si.QuantitySold,
                            ProductName = si.Product?.Name,
                            ProductPrice = si.Product?.Inventories.FirstOrDefault()?.Price,
                            TotalPrice = si.QuantitySold * (si.Product?.Inventories.FirstOrDefault()?.Price ?? 0)
                        }).ToList()
                    }).ToList()
                };

                return customerDto;
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "Error retrieving customer by username.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }
    }
}
