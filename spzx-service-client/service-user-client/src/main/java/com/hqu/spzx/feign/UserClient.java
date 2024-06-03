package com.hqu.spzx.feign;

import com.hqu.spzx.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
@Component
@FeignClient(value = "service-user")
public interface UserClient {
    @GetMapping("/api/user/userAddress/auth/findUserAddressList")
    List<UserAddress> findAddress(@RequestHeader("token") String token);
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    UserAddress findById(@PathVariable("id") Long id);
}
