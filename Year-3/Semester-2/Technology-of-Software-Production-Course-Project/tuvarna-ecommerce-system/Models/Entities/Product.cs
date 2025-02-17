﻿using System.ComponentModel.DataAnnotations;
using tuvarna_ecommerce_system.Models.Entities.Base;
using tuvarna_ecommerce_system.Models.Entities.Enums;

namespace tuvarna_ecommerce_system.Models.Entities
{
    public class Product : BaseEntity
    {
        [Required]
        [StringLength(128)]
        public string Name { get; set; }

        [Required]
        [StringLength(1024)]
        public string Description { get; set; }

        [Required]
        [StringLength(256)]
        public string ShortDescription { get; set; }

        [Required]
        public string ImageUrl { get; set; }

        [Required]
        [StringLength(6)]
        public string Sku { get; set; }

        [Required]
        public ProductTypeEnum ProductType { get; set; }

        public int? CategoryId { get; set; }

        public Category Category { get; set; }

        public ICollection<Tag> Tags { get; set; } = [];

        public bool IsFeatured { get; set; } = false;

        public ICollection<ProductImage> AdditionalImages { get; set; } = new List<ProductImage>();

        public ICollection<ProductInventory> Inventories { get; set; } = new List<ProductInventory>();

        public ICollection<SaleItem> SaleItems { get; set; } = new List<SaleItem>();
    }
}
