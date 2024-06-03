package com.hqu.spzx.serviceProduct.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.h5.ProductSkuDto;
import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.model.vo.h5.ProductItemVo;

import java.util.List;
import java.util.Map;

public interface ProductService {
    PageInfo<ProductSku> findByPage(ProductSkuDto productSkuDto, Integer page, Integer limit);

    ProductItemVo findBySkuId(Long skuId);

    ProductSku getById(Long skuId);
}
