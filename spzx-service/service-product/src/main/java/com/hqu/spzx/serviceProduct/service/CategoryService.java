package com.hqu.spzx.serviceProduct.service;

import com.hqu.spzx.model.entity.product.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllParent();

    List<Category> CategotyTree();
}
