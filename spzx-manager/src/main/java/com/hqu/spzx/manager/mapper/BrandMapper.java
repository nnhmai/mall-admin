package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.product.Brand;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BrandMapper {
    List<Brand> findByPage();

    int save(Brand brand);

    int updateById(Brand brand);

    int deleteById(Long id);

    List<Brand> findAll();

    Brand getById(Long brandId);
}
