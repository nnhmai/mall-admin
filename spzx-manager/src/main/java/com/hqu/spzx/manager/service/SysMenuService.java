package com.hqu.spzx.manager.service;

import com.hqu.spzx.model.entity.system.SysMenu;
import com.hqu.spzx.model.vo.system.SysMenuVo;

import java.util.List;

public interface SysMenuService {
    List<SysMenu> findAllNodes();

    int save(SysMenu sysMenu);

    int updateById(SysMenu sysMenu);

    int removeById(Long id);

    List<SysMenuVo> findMenuList();
}
