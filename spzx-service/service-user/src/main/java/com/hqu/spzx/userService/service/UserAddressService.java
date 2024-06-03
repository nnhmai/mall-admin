package com.hqu.spzx.userService.service;

import com.hqu.spzx.model.entity.user.UserAddress;

import java.util.List;

public interface UserAddressService {
    List<UserAddress> findUserAddress(String token);

    UserAddress findById(Long id);
}
