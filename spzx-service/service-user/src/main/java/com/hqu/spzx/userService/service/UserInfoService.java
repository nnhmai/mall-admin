package com.hqu.spzx.userService.service;

import com.hqu.spzx.model.dto.h5.UserLoginDto;
import com.hqu.spzx.model.dto.h5.UserRegisterDto;
import com.hqu.spzx.model.vo.h5.UserInfoVo;

public interface UserInfoService {
    void register(UserRegisterDto userRegisterDto);
    UserInfoVo getInfo(String token);
    String login(UserLoginDto userLoginDto);
}
