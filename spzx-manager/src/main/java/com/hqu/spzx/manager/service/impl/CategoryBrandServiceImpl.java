package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.CategoryBrandMapper;
import com.hqu.spzx.manager.service.CategoryBrandService;
import com.hqu.spzx.model.dto.product.CategoryBrandDto;
import com.hqu.spzx.model.entity.product.Brand;
import com.hqu.spzx.model.entity.product.CategoryBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryBrandServiceImpl implements CategoryBrandService {
    @Autowired
    private CategoryBrandMapper categoryBrandMapper;
    @Override
    public PageInfo<CategoryBrand> findByPage(Integer page, Integer limit, CategoryBrandDto categoryBrandDto) {
        PageHelper.startPage(page,limit);
        List<CategoryBrand> categoryBrandList=categoryBrandMapper.findByPage(categoryBrandDto);
        PageInfo<CategoryBrand> categoryBrandPageInfo = new PageInfo<>(categoryBrandList);
        return categoryBrandPageInfo;
    }

    @Override
    public int save(CategoryBrand categoryBrand) {
        return categoryBrandMapper.save(categoryBrand);
    }

    @Override
    public int updateById(CategoryBrand categoryBrand) {
        return categoryBrandMapper.updateById(categoryBrand);
    }

    @Override
    public int deleteById(Long id) {
        return categoryBrandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findBrandByCategoryId(Long categoryId) {
        List<Brand> brandList=categoryBrandMapper.findBrandByCategoryId(categoryId);
        return brandList;
    }

}
