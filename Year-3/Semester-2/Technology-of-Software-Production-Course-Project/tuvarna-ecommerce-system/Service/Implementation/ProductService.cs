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
        private readonly IProductImageRepository _productImageRepository;
        private readonly ITagRepository _tagRepository;
        private readonly IProductImageService _productImageService;
        private readonly ILogger<ProductService> _logger;

        public ProductService(IProductRepository repository, ICategoryRepository categoryRepository, ITagRepository tagRepository, ILogger<ProductService> logger, IProductImageService productImageService, IProductImageRepository productImageRepository)
        {
            _repository = repository;
            _categoryRepository = categoryRepository;
            _tagRepository = tagRepository;
            _logger = logger;
            _productImageService = productImageService;
            this._productImageRepository = productImageRepository;
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


                foreach (var imageDto in dto.Images)
                {
                    ProductImageCreateDTO imageCreateDTO = new ProductImageCreateDTO
                    {
                        ImageUrl = imageDto.ImageUrl,
                        ProductId = createdProduct.Id,
                    };
                    await _productImageService.CreateAsync(imageCreateDTO);
                }

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
                    Tags = createdProduct.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList(),
                    Images = createdProduct.AdditionalImages.Select(i => new ProductImageReadDTO { Id = i.Id, ImageUrl = i.ImageUrl }).ToList()
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

        public async Task<ProductReadAllDTO> GetAll()
        {
            try
            {
                var products = await _repository.GetAll();
                var productDtos = products.Select(p => new ProductReadDTO
                {
                    Id = p.Id,
                    Name = p.Name,
                    Sku = p.Sku,
                    Description = p.Description,
                    ShortDescription = p.ShortDescription,
                    ImageUrl = p.ImageUrl,
                    ProductType = p.ProductType.ToString(),
                    Category = p.Category != null ? new CategoryReadDTO
                    {
                        Id = p.Category.Id,
                        Name = p.Category.Name,
                        Description = p.Category.Description,
                        ImageUrl = p.Category.ImageUrl
                    } : null,
                    Tags = p.Tags.Select(t => new TagReadDTO
                    {
                        Id = t.Id,
                        Name = t.Name
                    }).ToList(),
                    Images = p.AdditionalImages.Select(i => new ProductImageReadDTO
                    {
                        Id = i.Id,
                        ImageUrl = i.ImageUrl
                    }).ToList(),
                    Inventories = p.Inventories.Select(i => new ProductInventoryReadDTO
                    {
                        Id = i.Id,
                        Price = i.Price,
                        DiscountPrice = i.DiscountPrice,
                        StockQuantity = i.StockQuantity,
                        ImportDate = i.ImportDate,
                        ProductId = i.ProductId
                    }).ToList(),
                    Sales = p.SaleItems.Select(s => new SaleItemReadDTO
                    {
                        Id = s.Id,
                        QuantitySold = s.QuantitySold

                    }).ToList()
                }).ToList();

                return new ProductReadAllDTO { Products = productDtos };
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<ProductReadAllDTO> GetByCategoryName(string name)
        {
            try
            {
                var products = await _repository.GetByCategoryName(name);
                var productDtos = products.Select(p => new ProductReadDTO
                {
                    Id = p.Id,
                    Name = p.Name,
                    Sku = p.Sku,
                    Description = p.Description,
                    ShortDescription = p.ShortDescription,
                    ImageUrl = p.ImageUrl,
                    ProductType = p.ProductType.ToString(),
                    Category = p.Category != null ? new CategoryReadDTO
                    {
                        Id = p.Category.Id,
                        Name = p.Category.Name,
                        Description = p.Category.Description,
                        ImageUrl = p.Category.ImageUrl
                    } : null,
                    Tags = p.Tags.Select(t => new TagReadDTO
                    {
                        Id = t.Id,
                        Name = t.Name
                    }).ToList(),
                    Images = p.AdditionalImages.Select(i => new ProductImageReadDTO
                    {
                        Id = i.Id,
                        ImageUrl = i.ImageUrl
                    }).ToList(),
                    Inventories = p.Inventories.Select(i => new ProductInventoryReadDTO
                    {
                        Id = i.Id,
                        Price = i.Price,
                        DiscountPrice = i.DiscountPrice,
                        StockQuantity = i.StockQuantity,
                        ImportDate = i.ImportDate,
                        ProductId = i.ProductId
                    }).ToList()
                }).ToList();

                return new ProductReadAllDTO { Products = productDtos };
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
                    Tags = product.Tags.Select(t => new TagReadDTO { Id = t.Id, Name = t.Name }).ToList(),
                    Images = product.AdditionalImages.Select(i => new ProductImageReadDTO { Id = i.Id, ImageUrl = i.ImageUrl }).ToList(),
                    Inventories = product.Inventories.Select(i => new ProductInventoryReadDTO
                    {
                        Id = i.Id,
                        Price = i.Price,
                        DiscountPrice = i.DiscountPrice,
                        StockQuantity = i.StockQuantity,
                        ImportDate = i.ImportDate,
                        ProductId = i.ProductId
                    }).ToList(),
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

                Category newCategory = null;
                if (updated.CategoryName != null)
                {
                    newCategory = await _categoryRepository.GetByNameAsync(updated.CategoryName);
                }

                var updatedProduct = new Product
                {
                    Id = updated.Id,
                    Name = updated.Name != null ? updated.Name : productToPatch.Name,
                    Description = updated.Description != null ? updated.Description : productToPatch.Description,
                    ShortDescription = updated.ShortDescription != null ? updated.ShortDescription : productToPatch.ShortDescription,
                    ImageUrl = updated.ImageUrl != null ? updated.ImageUrl : productToPatch.ImageUrl,
                    ProductType = updated.ProductType != null ? Enum.Parse<ProductTypeEnum>(updated.ProductType, true) : productToPatch.ProductType,
                    CategoryId = newCategory != null ? newCategory.Id : productToPatch.CategoryId,
                };

                var tags = new List<Tag>();
                if (updated.Tags != null) {
                    foreach (var tagDto in updated.Tags)
                    {
                        var tag = await _tagRepository.GetByNameAsync(tagDto.Name);
                        tags.Add(tag);
                    }
                    updatedProduct.Tags = tags;
                }

                var images = new List<ProductImage>();
                if (updated.Images != null)
                {
                    foreach (var imageDto in updated.Images)
                    {
                        var image = await _productImageRepository
                            .CreateAsync(new ProductImage { ImageUrl = imageDto.ImageUrl, ProductId = updated.Id });
                        images.Add(image);
                    }
                    updatedProduct.AdditionalImages = images;
                }

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
                    Category = patched.Category != null ? new CategoryReadDTO
                    {
                        Id = patched.Category.Id,
                        Name = patched.Category.Name,
                        Description = patched.Category.Description,
                        ImageUrl = patched.Category.ImageUrl
                    } : null,
                    Images = patched.AdditionalImages.Select(i => new ProductImageReadDTO { Id = i.Id, ImageUrl = i.ImageUrl }).ToList(),
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
