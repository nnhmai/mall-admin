package com.hqu.spzx.manager.service.impl;

import com.hqu.spzx.manager.mapper.ProductUnitMapper;
import com.hqu.spzx.manager.service.ProductUnitService;
import com.hqu.spzx.model.entity.base.ProductUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUnitServiceImpl implements ProductUnitService {
    @Autowired
    private ProductUnitMapper productUnitMapper;
    @Override
    public List<ProductUnit> findAll() {
        List<ProductUnit> productUnits=productUnitMapper.findAll();
        return productUnits;
    }
}
