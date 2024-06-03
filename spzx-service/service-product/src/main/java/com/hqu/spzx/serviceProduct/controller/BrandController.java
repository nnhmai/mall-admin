package com.hqu.spzx.serviceProduct.controller;

import com.hqu.spzx.model.entity.product.Brand;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.serviceProduct.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("findAll")
    public Result<List<Brand>> findAll(){
      List<Brand>brandList=  brandService.findAll();
      return Result.build(brandList, ResultCodeEnum.SUCCESS);
    }
}
