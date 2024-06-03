package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.dto.system.AssginRoleDto;
import com.hqu.spzx.model.dto.system.SysUserDto;
import com.hqu.spzx.model.entity.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SysUserMapper {
    int saveSysUser(SysUser sysUser);

    int updateSysUser(SysUser sysUser);

    int deleteById(Long userId);
    List<SysUser> findByPage(SysUserDto sysUserDto);

    int addAssign(@Param("userId") Long userId,@Param("roleId") Long roleId);
    int deleteAssign(@Param("userId") Long userId);
}
