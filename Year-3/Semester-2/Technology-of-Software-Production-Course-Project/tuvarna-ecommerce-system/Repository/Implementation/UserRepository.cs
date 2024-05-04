using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Data;
using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository.Implementation
{
    public class UserRepository : IUserRepository
    {

        private readonly EcommerceDbContext _context;

        public UserRepository(EcommerceDbContext context)
        {
            _context = context;
        }

        public async Task<User> CreateAsync(User user)
        {

            _context.Users.Add(user);
            await _context.SaveChangesAsync();
            return user;
        }

        public async Task<User> FindByEmail(string email)
        {
            return await _context.Users
                .FirstOrDefaultAsync(u => u.Email == email);
        }

        public async Task<User> FindByUsernameAsync(string username)
        {
            return await _context.Users
                .FirstOrDefaultAsync(u => u.Username == username);
        }
    }
}
