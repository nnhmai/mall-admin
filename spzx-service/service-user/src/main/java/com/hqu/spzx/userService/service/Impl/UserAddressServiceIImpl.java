package com.hqu.spzx.userService.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.hqu.spzx.model.entity.user.UserAddress;
import com.hqu.spzx.model.entity.user.UserInfo;
import com.hqu.spzx.userService.mapper.UserAddressMapper;
import com.hqu.spzx.userService.service.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAddressServiceIImpl implements UserAddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public List<UserAddress> findUserAddress(String token) {
        String userinfo = redisTemplate.opsForValue().get("token:" + token);
        UserInfo userInfo = JSON.parseObject(userinfo, UserInfo.class);
        List<UserAddress> userAddresses=userAddressMapper.findById(userInfo.getId());
        return userAddresses;
    }

    @Override
    public UserAddress findById(Long id) {
        return userAddressMapper.find(id);
    }
}
