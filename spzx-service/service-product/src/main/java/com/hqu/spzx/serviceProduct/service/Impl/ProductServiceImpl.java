package com.hqu.spzx.serviceProduct.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.h5.ProductSkuDto;
import com.hqu.spzx.model.entity.product.Product;
import com.hqu.spzx.model.entity.product.ProductDetails;
import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.model.vo.h5.ProductItemVo;
import com.hqu.spzx.serviceProduct.mapper.ProductMapper;
import com.hqu.spzx.serviceProduct.mapper.ProductSkuMapper;
import com.hqu.spzx.serviceProduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductSkuMapper productSkuMapper;
    @Override
    public PageInfo<ProductSku> findByPage(ProductSkuDto productSkuDto,Integer page, Integer limit) {
        PageHelper.startPage(page,limit);
        List<ProductSku> productSkuList = productMapper.findByPage(productSkuDto);
        PageInfo<ProductSku> productSkuPageInfo = new PageInfo<>(productSkuList);
        return productSkuPageInfo;
    }

    @Override
    public ProductItemVo findBySkuId(Long skuId) {
        Product product = productMapper.findBySkuId(skuId);
        ProductSku productSku=productSkuMapper.findById(skuId);
        ProductDetails productDetails = productMapper.findByProductId(product.getId());
        List<ProductSku> productSkuList = productSkuMapper.findByProductId(product.getId());
        Map<String,Object>map=new HashMap<>();
        productSkuList.forEach(item->{
            map.put(item.getSkuSpec(),item.getId());
        });
        ProductItemVo productItemVo=new ProductItemVo();
        productItemVo.setProduct(product);
        productItemVo.setProductSku(productSku);
        productItemVo.setSliderUrlList(Arrays.asList(product.getSliderUrls().split(",")));
        productItemVo.setDetailsImageUrlList(Arrays.asList(productDetails.getImageUrls().split(",")));
        productItemVo.setSpecValueList(JSON.parseArray(product.getSpecValue()));
        productItemVo.setSkuSpecValueMap(map);
        return productItemVo;
    }

    @Override
    public ProductSku getById(Long skuId) {
        return productSkuMapper.findById(skuId);
    }
}
