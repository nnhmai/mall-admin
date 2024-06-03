package com.hqu.spzx.serviceProduct.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.hqu.spzx.model.entity.product.Category;
import com.hqu.spzx.serviceProduct.mapper.CategoryMapper;
import com.hqu.spzx.serviceProduct.service.CategoryService;
import io.micrometer.common.util.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public List<Category> findAllParent() {
        List<Category> categoryList=categoryMapper.findByParent();
        return categoryList;
    }

    @Override
    @Cacheable(value = "category",key="'all'")
    public List<Category> CategotyTree() {
        List<Category> categoryList=categoryMapper.findAll();
        List<Category> oneCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == 0).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(oneCategoryList)) {
            oneCategoryList.forEach(oneCategory -> {
                List<Category> twoCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == oneCategory.getId().longValue()).collect(Collectors.toList());
                oneCategory.setChildren(twoCategoryList);
                if(!CollectionUtils.isEmpty(twoCategoryList)) {
                    twoCategoryList.forEach(twoCategory -> {
                        List<Category> threeCategoryList = categoryList.stream().filter(item -> item.getParentId().longValue() == twoCategory.getId().longValue()).collect(Collectors.toList());
                        twoCategory.setChildren(threeCategoryList);
                    });
                }
            });
        }

        return oneCategoryList;
    }
}
