package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.dto.product.ProductDto;
import com.hqu.spzx.model.entity.product.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> findByPage(ProductDto productDto);

    void save(Product product);

    Product getById(Long id);

    void updateById(Product product);

    void deletedById(Long id);
}
