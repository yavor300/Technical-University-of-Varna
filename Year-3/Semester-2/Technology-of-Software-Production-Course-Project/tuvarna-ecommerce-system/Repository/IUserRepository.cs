using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface IUserRepository
    {

        Task<User> CreateAsync(User user);

        Task<User> FindByEmail(String email);

        Task<User> FindByUsernameAsync(String username);

    }
}

