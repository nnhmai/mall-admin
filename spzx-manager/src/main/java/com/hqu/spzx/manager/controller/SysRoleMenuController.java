package com.hqu.spzx.manager.controller;

import com.hqu.spzx.manager.service.SysRoleMenuService;
import com.hqu.spzx.model.dto.system.AssginMenuDto;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @GetMapping(value = "/findSysRoleMenuByRoleId/{roleId}")
    public Result<Map<String , Object>> findSysRoleMenuByRoleId(@PathVariable(value = "roleId") Long roleId) {
        Map<String,Object> resultMap=sysRoleMenuService.findByRoleId(roleId);
        return Result.build(resultMap, ResultCodeEnum.SUCCESS);
    }
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto) {
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
