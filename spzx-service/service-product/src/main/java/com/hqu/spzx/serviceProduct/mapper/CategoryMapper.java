package com.hqu.spzx.serviceProduct.mapper;

import com.hqu.spzx.model.entity.product.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findByParent();

    List<Category> findAll();
}
