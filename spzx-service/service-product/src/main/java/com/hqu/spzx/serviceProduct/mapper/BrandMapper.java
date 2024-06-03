package com.hqu.spzx.serviceProduct.mapper;

import com.hqu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findAll();
}
