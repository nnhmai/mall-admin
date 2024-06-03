package com.hqu.spzx.userService.mapper;

import com.hqu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    void save(UserInfo userInfo);

    UserInfo getByUsername(String username);
}
