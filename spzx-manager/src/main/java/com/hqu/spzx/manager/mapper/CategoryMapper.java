package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.product.Category;
import com.hqu.spzx.model.vo.product.CategoryExcelVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
     List<Category> findByParentId(Long parentId);

     int countByParentId(Long parentId);

     List<Category> selectAll();

    int batchInsert(List<CategoryExcelVo> cachedDataList);

    Category getByID(Long categoryId);
}
