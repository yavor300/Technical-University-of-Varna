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
        private readonly ILogger<ProductService> _logger;

        public UserService(IUserRepository userRepository, IPasswordHasher<User> passwordHasher, ILogger<ProductService> logger)
        {
            _userRepository = userRepository;
            _passwordHasher = passwordHasher;
            _logger = logger;
        }

        public async Task<UserReadDTO> CreateAsync(UserCreateDTO userCreateDTO)
        {
            try
            {
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
    }
}
