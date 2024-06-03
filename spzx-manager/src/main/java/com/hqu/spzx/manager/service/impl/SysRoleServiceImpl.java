package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.SysRoleMapper;
import com.hqu.spzx.manager.service.SysRoleService;
import com.hqu.spzx.model.entity.system.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleServiceImpl implements SysRoleService {
   @Autowired
   private SysRoleMapper sysRoleMapper;

    @Override
    public PageInfo<SysRole> findByPage(Integer pageSize, Integer pageNum, String roleName) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysRole> sysRoles = sysRoleMapper.findByPage(roleName);
        PageInfo<SysRole> sysRolePageInfo = new PageInfo<>(sysRoles);
        return sysRolePageInfo;
    }

    @Override
    public int saveSysRole(SysRole sysRole) {

        return sysRoleMapper.save(sysRole);
    }

    @Override
    public int updateSysRole(SysRole sysRole) {
        return sysRoleMapper.update(sysRole);
    }

    @Override
    public int deleteById(Long roleId) {
        return sysRoleMapper.delete(roleId);
    }

    @Override
    public Map<String, List<SysRole>> findAllRoles(Long userId) {
        List<SysRole> sysRoleList=sysRoleMapper.findAllRoles(userId);
        Map<String,List<SysRole>> resultMap=new HashMap<>();
        resultMap.put("allRolesList",sysRoleList);
        return resultMap;
    }
}
