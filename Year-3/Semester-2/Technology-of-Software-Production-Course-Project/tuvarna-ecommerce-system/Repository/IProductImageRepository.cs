﻿using tuvarna_ecommerce_system.Models.Entities;

namespace tuvarna_ecommerce_system.Repository
{
    public interface IProductImageRepository
    {

        Task<ProductImage> CreateAsync(ProductImage entity);
    }
}
