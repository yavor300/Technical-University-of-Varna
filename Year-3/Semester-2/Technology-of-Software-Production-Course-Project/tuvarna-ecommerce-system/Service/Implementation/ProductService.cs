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
                Category category = null;
                if (!string.IsNullOrWhiteSpace(dto.CategoryName))
                {
                    category = await _categoryRepository.GetByNameAsync(dto.CategoryName);
                }

                var productType = Enum.Parse<ProductTypeEnum>(dto.ProductType, true);

                var product = new Product
                {
                    Name = dto.Name,
                    Description = dto.Description,
                    ShortDescription = dto.ShortDescription,
                    ImageUrl = dto.ImageUrl,
                    ProductType = productType,
                    CategoryId = category?.Id
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
                    Category = category == null ? null : new CategoryReadDTO
                    {
                        Id = category.Id,
                        Name = category.Name,
                        Description = category.Description,
                        ImageUrl = category.ImageUrl
                    },
                    Tags = createdProduct.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList()
                };

                return response;
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
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

        public async Task<ProductReadDTO> GetByIdAsync(ProductGetByIdDTO dto)
        {

            try
            {
                var product = await _repository.GetByIdAsync(dto.Id);


                CategoryReadDTO categoryDto = null;
                if (product.CategoryId.HasValue) 
                {
                    var category = await _categoryRepository.GetByIdAsync(product.CategoryId.Value);
                    if (category != null)
                    {
                        categoryDto = new CategoryReadDTO
                        {
                            Id = category.Id,
                            Name = category.Name,
                            Description = category.Description,
                            ImageUrl = category.ImageUrl
                        };
                    }
                }


                return new ProductReadDTO
                {
                    Id = product.Id,
                    Name = product.Name,
                    Sku = product.Sku,
                    Description = product.Description,
                    ShortDescription = product.ShortDescription,
                    ImageUrl = product.ImageUrl,
                    ProductType = product.ProductType.ToString(),
                    Category = categoryDto,
                    Tags = product.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList()
                };
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

        public async Task<ProductReadDTO> PatchAsync(ProductPatchDTO updated)
        {
            try
            {
                var productToPatch = await _repository.GetByIdAsync(updated.Id);

                var newCategory = await _categoryRepository.GetByNameAsync(updated.CategoryName);

                var productType = Enum.Parse<ProductTypeEnum>(updated.ProductType, true);

                var updatedProduct = new Product
                {
                    Id = updated.Id,
                    Name = updated.Name,
                    Description = updated.Description,
                    ShortDescription = updated.ShortDescription,
                    ImageUrl = updated.ImageUrl,
                    ProductType = productType,
                    CategoryId = newCategory.Id
                };

                var tags = new List<Tag>();

                foreach (var tagDto in updated.Tags)
                {
                    var tag = await _tagRepository.GetByNameAsync(tagDto.Name);
                    tags.Add(tag);
                }
                updatedProduct.Tags = tags;

                var patched = await _repository.PatchAsync(updatedProduct);

                var response = new ProductReadDTO
                {
                    Id = patched.Id,
                    Name = patched.Name,
                    Sku = patched.Sku,
                    Description = patched.Description,
                    ShortDescription = patched.ShortDescription,
                    ImageUrl = patched.ImageUrl,
                    ProductType = patched.ProductType.ToString(),
                    Category = new CategoryReadDTO
                    {
                        Id = newCategory.Id,
                        Name = newCategory.Name,
                        Description = newCategory.Description,
                        ImageUrl = newCategory.ImageUrl
                    },
                    Tags = patched.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList()
                };

                return response;
            }
            catch (ArgumentException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
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
