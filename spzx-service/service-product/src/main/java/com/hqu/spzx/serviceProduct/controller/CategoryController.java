package com.hqu.spzx.serviceProduct.controller;

import com.hqu.spzx.model.entity.product.Category;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.serviceProduct.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name = "分类接口管理")
@RestController
@RequestMapping(value = "/api/product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @GetMapping("findCategoryTree")
    public Result<List<Category>> TreeCategory(){
        List<Category> categoryList=categoryService.CategotyTree();
        return Result.build(categoryList, ResultCodeEnum.SUCCESS);
    }
}
