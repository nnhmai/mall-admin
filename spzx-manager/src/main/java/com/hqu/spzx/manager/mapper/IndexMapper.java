package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IndexMapper {
   SysUser selectByUsername(String username);
}
