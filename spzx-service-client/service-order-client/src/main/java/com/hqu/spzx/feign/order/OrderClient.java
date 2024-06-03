package com.hqu.spzx.feign.order;

import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-order")
@Component
public interface OrderClient {
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    Result<OrderInfo> findByOrderNo(@PathVariable String orderNo);
}
