package com.hqu.spzx.manager.controller;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.service.SysUserService;
import com.hqu.spzx.model.dto.system.AssginRoleDto;
import com.hqu.spzx.model.dto.system.SysUserDto;
import com.hqu.spzx.model.entity.system.SysRole;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService ;

    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysRole>> findByPage(SysUserDto sysUserDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize) {
        PageInfo<SysUser> pageInfo = sysUserService.findByPage(sysUserDto , pageNum , pageSize) ;
        return Result.build(pageInfo , ResultCodeEnum.SUCCESS) ;
    }
    @PostMapping(value = "/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser) {
        sysUserService.saveSysUser(sysUser) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PutMapping(value = "/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser) {
        sysUserService.updateSysUser(sysUser) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @DeleteMapping(value = "/deleteById/{userId}")
    public Result deleteById(@PathVariable(value = "userId") Long userId) {
        sysUserService.deleteById(userId) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
