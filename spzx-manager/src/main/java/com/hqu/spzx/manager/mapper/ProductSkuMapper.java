package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.product.ProductSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductSkuMapper {
    void batchSave(List<ProductSku> productSkuList);

    List<ProductSku> selectByProductId(Long id);

    void update(ProductSku productSku);

    void deleteById(Long id);
}
