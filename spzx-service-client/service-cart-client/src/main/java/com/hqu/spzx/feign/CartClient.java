package com.hqu.spzx.feign;

import com.hqu.spzx.model.entity.h5.CartInfo;
import com.hqu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Component
@FeignClient(value = "service-cart")
public interface CartClient {
    @GetMapping("api/order/cart/auth/getAllChecked")
    public List<CartInfo> AllChecked(@RequestHeader String token);
    @GetMapping(value = "/api/order/cart/auth/deleteChecked")
    public abstract Result deleteChecked(@RequestHeader String token) ;
}
