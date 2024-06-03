package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysMenuMapper {
    List<SysMenu> findAllNodes();

    int save(SysMenu sysMenu);

    int updateById(SysMenu sysMenu);

    int deleteById(Long id);

    List<SysMenu> findMenuById(Long userId);
    List<SysMenu> findByParentIdSysMenus(Long parentId);
}
