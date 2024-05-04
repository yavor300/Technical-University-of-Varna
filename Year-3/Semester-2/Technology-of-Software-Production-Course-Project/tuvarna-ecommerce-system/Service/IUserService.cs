using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface IUserService
    {

        Task<UserReadDTO> CreateAsync(UserCreateDTO userCreateDTO);
    }
}
