package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.dto.system.AssginMenuDto;
import com.hqu.spzx.model.entity.system.SysMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface SysRoleMenuMapper {
    List<Long> findByRoleId(Long roleId);

    int deleteAssign(Long roleId);

    int addAssign(AssginMenuDto assginMenuDto);
}
