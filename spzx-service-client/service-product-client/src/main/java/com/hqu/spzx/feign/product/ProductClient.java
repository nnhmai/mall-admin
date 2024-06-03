package com.hqu.spzx.feign.product;

import com.hqu.spzx.model.entity.product.ProductSku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-product")
@Component
public interface ProductClient {
    @GetMapping("/api/product/getById/{skuId}")
    ProductSku getDetails(@PathVariable Long skuId);
}
