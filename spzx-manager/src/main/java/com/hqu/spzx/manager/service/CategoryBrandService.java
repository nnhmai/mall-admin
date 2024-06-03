package com.hqu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.product.CategoryBrandDto;
import com.hqu.spzx.model.entity.product.Brand;
import com.hqu.spzx.model.entity.product.CategoryBrand;

import java.util.List;

public interface CategoryBrandService {
    PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto);

    int save(CategoryBrand categoryBrand);

    int updateById(CategoryBrand categoryBrand);

    int deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
