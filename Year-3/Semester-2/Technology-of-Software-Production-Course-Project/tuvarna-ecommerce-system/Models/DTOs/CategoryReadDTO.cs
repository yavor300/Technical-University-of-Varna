﻿namespace tuvarna_ecommerce_system.Models.DTOs
{
    public class CategoryReadDTO
    {
        public int Id { get; set; }
        public string Name { get; set; } = string.Empty;
        public string? Description { get; set; }
        public string? ImageUrl { get; set; }
    }
}
