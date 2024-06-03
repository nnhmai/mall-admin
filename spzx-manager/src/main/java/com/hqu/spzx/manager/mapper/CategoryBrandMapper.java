package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.dto.product.CategoryBrandDto;
import com.hqu.spzx.model.entity.product.Brand;
import com.hqu.spzx.model.entity.product.CategoryBrand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CategoryBrandMapper {
    List<CategoryBrand> findByPage(CategoryBrandDto categoryBrandDto);

    int save(CategoryBrand categoryBrand);

    int updateById(CategoryBrand categoryBrand);

    int deleteById(Long id);

    List<Brand> findBrandByCategoryId(Long categoryId);
}
