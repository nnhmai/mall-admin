package com.hqu.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.service.SysRoleService;
import com.hqu.spzx.model.dto.system.SysRoleDto;
import com.hqu.spzx.model.entity.system.SysRole;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin/system/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @PostMapping("/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(@RequestBody SysRoleDto sysRoleDto,
                                       @PathVariable(value = "pageNum") Integer pageNum ,
                                       @PathVariable(value = "pageSize") Integer pageSize){
        return Result.build(sysRoleService.findByPage(pageSize,pageNum,sysRoleDto.getRoleName()), ResultCodeEnum.SUCCESS);
    }
    @PostMapping(value = "/saveSysRole")
    public Result saveSysRole(@RequestBody SysRole SysRole) {
        sysRoleService.saveSysRole(SysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PutMapping(value = "/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole) {
        sysRoleService.updateSysRole(sysRole) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @DeleteMapping(value = "/deleteById/{roleId}")
    public Result deleteById(@PathVariable(value = "roleId") Long roleId) {
        sysRoleService.deleteById(roleId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @GetMapping(value = "/findAllRoles/{userId}")
    public Result<Map<String, List<SysRole>>> findAllRoles(@PathVariable Long userId){
        Map<String,List<SysRole>> resultMap=sysRoleService.findAllRoles(userId);
        return Result.build(resultMap,ResultCodeEnum.SUCCESS);
    }
}
