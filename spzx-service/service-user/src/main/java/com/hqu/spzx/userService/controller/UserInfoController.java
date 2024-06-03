package com.hqu.spzx.userService.controller;

import com.hqu.spzx.model.dto.h5.UserLoginDto;
import com.hqu.spzx.model.dto.h5.UserRegisterDto;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.h5.UserInfoVo;
import com.hqu.spzx.properties.MinioProperties;
import com.hqu.spzx.userService.service.UserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/user/userInfo")
@EnableConfigurationProperties( MinioProperties.class)
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("register")
    public Result register(UserRegisterDto userRegisterDto){
        userInfoService.register(userRegisterDto);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @Operation(summary = "会员登录")
    @PostMapping("login")
    public Result login(@RequestBody UserLoginDto userLoginDto) {
        return Result.build(userInfoService.login(userLoginDto), ResultCodeEnum.SUCCESS);
    }
    @GetMapping("auth/getCurrentUserInfo")
    public Result getInfo(@RequestHeader("token") String token){
        UserInfoVo userInfoVo=userInfoService.getInfo(token);
        return Result.build(userInfoVo, ResultCodeEnum.SUCCESS);
    }
}
