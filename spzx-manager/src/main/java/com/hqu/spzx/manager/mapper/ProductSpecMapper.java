package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.product.ProductSpec;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSpecMapper {
    List<ProductSpec> findByPage();

    int save(ProductSpec productSpec);

    int updateById(ProductSpec productSpec);

    int deleteById(Long id);


}
