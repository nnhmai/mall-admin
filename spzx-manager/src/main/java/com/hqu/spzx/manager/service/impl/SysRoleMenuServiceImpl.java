package com.hqu.spzx.manager.service.impl;

import com.hqu.spzx.manager.mapper.SysRoleMenuMapper;
import com.hqu.spzx.manager.service.SysMenuService;
import com.hqu.spzx.manager.service.SysRoleMenuService;
import com.hqu.spzx.model.dto.system.AssginMenuDto;
import com.hqu.spzx.model.entity.system.SysMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Override
    public Map<String, Object> findByRoleId(Long roleId) {
        List<SysMenu> allNodes = sysMenuService.findAllNodes();
        List<Long> sysMenuIds=sysRoleMenuMapper.findByRoleId(roleId);
        Map<String,Object>result=new HashMap<>();
        result.put("sysMenuList",allNodes);
        result.put("roleMenuIds",sysMenuIds);
        return result;
    }
    @Transactional
    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        sysRoleMenuMapper.deleteAssign(assginMenuDto.getRoleId());
        List<Map<String, Number>> menuIdList = assginMenuDto.getMenuIdList();
        if(!CollectionUtils.isEmpty(menuIdList)){
            sysRoleMenuMapper.addAssign(assginMenuDto);
        }

    }
}
