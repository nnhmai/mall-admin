package com.hqu.spzx.serviceProduct.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.h5.ProductSkuDto;
import com.hqu.spzx.model.dto.product.ProductDto;
import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.h5.ProductItemVo;
import com.hqu.spzx.serviceProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("{page}/{limit}")
    public Result<PageInfo<ProductSku>> findByPage(ProductSkuDto productSkuDto,
                                                   @PathVariable("page") Integer page,
                                                   @PathVariable("limit") Integer limit){
        PageInfo<ProductSku> productSkuPageInfo = productService.findByPage(productSkuDto, page, limit);
        return Result.build(productSkuPageInfo, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("item/{skuId}")
    public Result<ProductItemVo> findBySkuId(@PathVariable Long skuId){
        ProductItemVo productItemVo=productService.findBySkuId(skuId);
        return Result.build(productItemVo,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("getById/{skuId}")
    public ProductSku getById(@PathVariable Long skuId){
        return productService.getById(skuId);
    }
}
