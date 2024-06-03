package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.product.ProductDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProductDetailsMapper {
    void save(ProductDetails productDetails);

    ProductDetails selectByProductId(Long id);

    void updateById(@Param("id") Long id,@Param("imageUrls") String imageUrls);

    void deleteById(Long id);
}
