package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.ProductSpecMapper;
import com.hqu.spzx.manager.service.ProductSpecService;
import com.hqu.spzx.model.entity.product.ProductSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSpecServiceImpl implements ProductSpecService {
    @Autowired
    private ProductSpecMapper productSpecMapper;
    @Override
    public PageInfo<ProductSpec> findByPage(Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<ProductSpec> productSpecList=productSpecMapper.findByPage();
        PageInfo<ProductSpec> productSpecPageInfo = new PageInfo<>(productSpecList);
        return productSpecPageInfo;
    }

    @Override
    public int save(ProductSpec productSpec) {
        return productSpecMapper.save(productSpec);
    }

    @Override
    public int updateById(ProductSpec productSpec) {
        return productSpecMapper.updateById(productSpec);
    }

    @Override
    public int deleteById(Long id) {
        return productSpecMapper.deleteById(id);
    }

    @Override
    public List<ProductSpec> findAll() {
        List<ProductSpec>productSpecs=productSpecMapper.findByPage();
        return productSpecs;
    }
}
