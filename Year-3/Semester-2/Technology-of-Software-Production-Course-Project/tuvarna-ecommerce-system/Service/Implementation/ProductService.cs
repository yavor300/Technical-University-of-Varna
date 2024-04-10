using tuvarna_ecommerce_system.Data.Repositories;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities.Enums;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;
using tuvarna_ecommerce_system.Exceptions;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class ProductService : IProductService
    {

        private readonly IProductRepository _repository;
        private readonly ICategoryRepository _categoryRepository;
        private readonly ITagRepository _tagRepository;
        private readonly ILogger<ProductService> _logger;

        public ProductService(IProductRepository repository, ICategoryRepository categoryRepository, ITagRepository tagRepository, ILogger<ProductService> logger)
        {
            _repository = repository;
            _categoryRepository = categoryRepository;
            _tagRepository = tagRepository;
            _logger = logger;
        }

        public async Task<ProductReadDTO> AddAsync(ProductCreateDTO dto)
        {
            try
            {
                var category = await _categoryRepository.GetByNameAsync(dto.CategoryName);

                var productType = Enum.Parse<ProductTypeEnum>(dto.ProductType, true);

                var product = new Product
                {
                    Name = dto.Name,
                    Description = dto.Description,
                    ShortDescription = dto.ShortDescription,
                    ImageUrl = dto.ImageUrl,
                    ProductType = productType,
                    CategoryId = category.Id
                };

                var tags = new List<Tag>();

                foreach (var tagDto in dto.Tags)
                {
                    var tag = await _tagRepository.GetByNameAsync(tagDto.Name);
                    tags.Add(tag);
                }

                var createdProduct = await _repository.CreateAsync(product, tags);

                var response = new ProductReadDTO
                {
                    Id = createdProduct.Id,
                    Name = createdProduct.Name,
                    Sku = createdProduct.Sku,
                    Description = createdProduct.Description,
                    ShortDescription = createdProduct.ShortDescription,
                    ImageUrl = createdProduct.ImageUrl,
                    ProductType = createdProduct.ProductType.ToString(),
                    CategoryName = category.Name,
                    Tags = createdProduct.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList()
                };

                return response;
            }
            catch (EntityNotFoundException ex)
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
