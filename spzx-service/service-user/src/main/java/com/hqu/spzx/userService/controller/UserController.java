package com.hqu.spzx.userService.controller;

import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user/sms")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/sendCode/{phone}")
    public Result SendPhoneCode(@PathVariable String phone){
        userService.sendPoneCode(phone);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
}
