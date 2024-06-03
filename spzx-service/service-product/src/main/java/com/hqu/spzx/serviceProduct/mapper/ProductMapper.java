package com.hqu.spzx.serviceProduct.mapper;

import com.hqu.spzx.model.dto.h5.ProductSkuDto;
import com.hqu.spzx.model.entity.product.Product;
import com.hqu.spzx.model.entity.product.ProductDetails;
import com.hqu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ProductMapper {
    List<ProductSku> findByPage(ProductSkuDto productSkuDto);

    Product findBySkuId(Long skuId);
    ProductDetails findByProductId(Long ProductId);
}
