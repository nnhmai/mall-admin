package com.hqu.spzx.userService.service.Impl;

import com.alibaba.fastjson.JSON;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.model.dto.h5.UserLoginDto;
import com.hqu.spzx.model.dto.h5.UserRegisterDto;
import com.hqu.spzx.model.entity.user.UserInfo;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.h5.UserInfoVo;
import com.hqu.spzx.userService.mapper.UserInfoMapper;
import com.hqu.spzx.userService.service.UserInfoService;
import io.micrometer.common.util.StringUtils;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @SneakyThrows
    @Override
    @Transactional
    public void register(UserRegisterDto userRegisterDto) {
        if(StringUtils.isEmpty(userRegisterDto.getNickName())||
           StringUtils.isEmpty(userRegisterDto.getPassword())||
            StringUtils.isEmpty(userRegisterDto.getUsername())||
            StringUtils.isEmpty(userRegisterDto.getCode())){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
        UserInfo user=userInfoMapper.getByUsername(userRegisterDto.getUsername());
        if(null!=user){
            throw new MyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        String code = redisTemplate.opsForValue().get("phone:code"+userRegisterDto.getUsername());
        if(!code.equals(userRegisterDto.getCode())){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        UserInfo userInfo=new UserInfo();
        userInfo.setNickName(userInfo.getNickName());
        userInfo.setPassword(DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes(StandardCharsets.UTF_8)));
        userInfo.setPhone(userInfo.getPhone());
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        userInfoMapper.save(userInfo);
        redisTemplate.delete("phone:code");
    }

    @SneakyThrows
    @Override
    public String login(UserLoginDto userLoginDto) {
        String password = userLoginDto.getPassword();
        String username = userLoginDto.getUsername();
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
        UserInfo byUsername = userInfoMapper.getByUsername(username);
        if(null==byUsername||
                !byUsername.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        if(byUsername.getStatus()==0){
            throw new MyException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token= UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("token:"+token, JSON.toJSONString(byUsername),30, TimeUnit.DAYS);
        return token;
    }
    @SneakyThrows
    @Override
    public UserInfoVo getInfo(String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        if(null==userInfo){
            throw new MyException(ResultCodeEnum.LOGIN_AUTH);
        }
        UserInfoVo userInfoVo=new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }
}
