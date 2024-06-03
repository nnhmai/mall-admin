package com.hqu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.entity.product.ProductSpec;

import java.util.List;

public interface ProductSpecService {
    PageInfo<ProductSpec> findByPage(Integer page, Integer limit);

    int save(ProductSpec productSpec);

    int updateById(ProductSpec productSpec);

    int deleteById(Long id);

    List<ProductSpec> findAll();

}
