package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.BrandMapper;
import com.hqu.spzx.manager.service.BrandService;
import com.hqu.spzx.model.entity.product.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandMapper brandMapper;
    @Override
    public PageInfo<Brand> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<Brand> brandList=brandMapper.findByPage();
        PageInfo<Brand> brandPageInfo = new PageInfo<>(brandList);
        return brandPageInfo;
    }

    @Override
    public int save(Brand brand) {
        return brandMapper.save(brand);
    }

    @Override
    public int updateById(Brand brand) {
        return brandMapper.updateById(brand);
    }

    @Override
    public int deleteById(Long id) {
        return brandMapper.deleteById(id);
    }

    @Override
    public List<Brand> findAll() {
        return brandMapper.findAll();
    }
}
