package com.hqu.spzx.serviceProduct.mapper;

import com.hqu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    List<ProductSku> findByTopSale();

    ProductSku findById(Long skuId);
    List<ProductSku> findByProductId(Long product_id);
}
