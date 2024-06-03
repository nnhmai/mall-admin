package com.hqu.spzx.manager.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.entity.system.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
    PageInfo<SysRole> findByPage(Integer pageSize, Integer pageNum, String roleName);

    int saveSysRole(SysRole sysRole);

    int updateSysRole(SysRole sysRole);

    int deleteById(Long roleId);

    Map<String, List<SysRole>> findAllRoles(Long userId);
}
