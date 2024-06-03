package com.hqu.spzx.userService.controller;

import com.hqu.spzx.model.entity.user.UserAddress;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.userService.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user/userAddress")
public class UserAddressController {
    @Autowired
    private UserAddressService userAddressService;
    @GetMapping("auth/findUserAddressList")
    public Result<List<UserAddress>> findAddress(@RequestHeader String token){
        List<UserAddress>userAddresses=userAddressService.findUserAddress(token);
        return Result.build(userAddresses, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("getUserAddress/{id}")
    public UserAddress findById(@PathVariable Long id){
       return userAddressService.findById(id);
    }

}
