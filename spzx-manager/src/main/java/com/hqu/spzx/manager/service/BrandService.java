package com.hqu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.entity.product.Brand;

import java.util.List;

public interface BrandService {
    PageInfo<Brand> findByPage(Integer page, Integer limit);

    int save(Brand brand);

    int updateById(Brand brand);

    int deleteById(Long id);

    List<Brand> findAll();
}
