package com.hqu.spzx.serviceProduct.service.Impl;

import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.serviceProduct.mapper.ProductSkuMapper;
import com.hqu.spzx.serviceProduct.service.ProductSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public List<ProductSku> findTop() {
        List<ProductSku> productSkus=productSkuMapper.findByTopSale();
        return productSkus;
    }
}
