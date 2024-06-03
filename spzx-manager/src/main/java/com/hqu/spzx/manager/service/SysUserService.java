package com.hqu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.system.AssginRoleDto;
import com.hqu.spzx.model.dto.system.SysUserDto;
import com.hqu.spzx.model.entity.system.SysUser;

public interface SysUserService {
    PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize);

    int saveSysUser(SysUser sysUser);

    int updateSysUser(SysUser sysUser);

    int deleteById(Long userId);

    void doAssign(AssginRoleDto assginRoleDto);
}
