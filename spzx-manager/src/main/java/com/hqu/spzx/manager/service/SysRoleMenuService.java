package com.hqu.spzx.manager.service;

import com.hqu.spzx.model.dto.system.AssginMenuDto;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface SysRoleMenuService {
    Map<String, Object> findByRoleId(Long roleId);

    void doAssign(AssginMenuDto assginMenuDto);
}
