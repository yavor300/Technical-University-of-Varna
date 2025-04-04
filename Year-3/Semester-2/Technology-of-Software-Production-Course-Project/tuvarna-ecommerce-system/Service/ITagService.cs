﻿using tuvarna_ecommerce_system.Models.DTOs;

namespace tuvarna_ecommerce_system.Service
{
    public interface ITagService
    {

        Task<TagReadDTO> AddTagAsync(TagCreateDTO dto);

        Task<TagReadDTO> PatchTagAsync(TagPatchDTO dto);

        Task<TagReadAllDTO> GetAllTagsAsync();

        Task<TagReadDTO> Delete(int id);
    }
}
