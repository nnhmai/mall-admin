package com.hqu.spzx.serviceProduct.service.Impl;

import com.hqu.spzx.model.entity.product.Brand;
import com.hqu.spzx.serviceProduct.mapper.BrandMapper;
import com.hqu.spzx.serviceProduct.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    @Cacheable(value = "brandList",unless = "#result.size()==0")
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
