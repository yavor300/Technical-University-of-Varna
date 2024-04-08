using Microsoft.EntityFrameworkCore;
using tuvarna_ecommerce_system.Exceptions;
using tuvarna_ecommerce_system.Models.DTOs;
using tuvarna_ecommerce_system.Models.Entities;
using tuvarna_ecommerce_system.Repository;
using tuvarna_ecommerce_system.Utils;

namespace tuvarna_ecommerce_system.Service.Implementation
{
    public class TagService : ITagService
    {

        private readonly ITagRepository _tagRepository;
        private readonly ILogger<TagService> _logger;

        public TagService(ITagRepository tagRepository, ILogger<TagService> logger)
        {
            _tagRepository = tagRepository;
            _logger = logger;
        }

        public async Task<TagReadDTO> AddTagAsync(TagCreateDTO dto)
        {
            try
            {
                var tag = new Tag
                {
                    Name = dto.Name.ToLowerInvariant()
                };

                tag = await _tagRepository.CreateAsync(tag);

                var tagReadDto = new TagReadDTO
                {
                    Id = tag.Id,
                    Name = tag.Name
                };

                return tagReadDto;
            }
            catch (DbUpdateException ex)
            {
                ExceptionHandlerUtil.HandleDbUpdateException<TagService>(_logger, ex, dto.Name,
                    "Attempted to add a duplicate tag: {EntityName}",
                    "Database exception occurred while adding a new tag. {EntityName}"
                    );
                throw;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }

        public async Task<TagReadDTO> PatchTagAsync(TagPatchDTO dto)
        {

            try
            {
                var updated = await _tagRepository.PatchAsync(dto.Id, dto.Name);

                return new TagReadDTO
                {
                    Id = updated.Id,
                    Name = updated.Name
                };
            }
            catch (EntityNotFoundException ex)
            {
                _logger.LogError(ex, ex.Message);
                throw;
            }
            catch (InvalidOperationException ex)
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

        public async Task<TagReadAllDTO> GetAllTagsAsync()
        {
            try
            {
                var tags = await _tagRepository.GetAllAsync();

                var tagsDtos = tags.Select(tag => new TagReadDTO
                {
                    Id = tag.Id,
                    Name = tag.Name
                }).ToList();

                var result = new TagReadAllDTO
                {
                    Tags = tagsDtos
                };

                return result;
            }
            catch (Exception ex)
            {
                _logger.LogError(ex, "An unexpected error occurred.");
                throw new InternalServerErrorException("An unexpected error occurred. Please try again later.", ex);
            }
        }
    }
}
