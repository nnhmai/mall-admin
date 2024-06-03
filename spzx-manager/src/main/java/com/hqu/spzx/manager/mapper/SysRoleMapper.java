package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface SysRoleMapper {
    List<SysRole> findByPage(String roleName);

    int save(@Param("sysRole") SysRole sysRole);

    int update(@Param("sysRole")SysRole sysRole);

    int delete(Long roleId);

    List<SysRole> findAllRoles(Long userId);
}
