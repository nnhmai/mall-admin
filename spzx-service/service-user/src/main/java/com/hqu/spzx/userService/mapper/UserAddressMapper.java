package com.hqu.spzx.userService.mapper;

import com.hqu.spzx.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    List<UserAddress> findById(Long id);
    UserAddress find(Long id);
}
