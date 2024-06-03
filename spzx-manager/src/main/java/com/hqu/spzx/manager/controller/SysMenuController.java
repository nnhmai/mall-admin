package com.hqu.spzx.manager.controller;

import com.hqu.spzx.manager.service.SysMenuService;
import com.hqu.spzx.model.entity.system.SysMenu;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @GetMapping("/findNodes")
    public Result<List<SysMenu>> findNodes() {
        List<SysMenu> list = sysMenuService.findAllNodes();
        return Result.build(list , ResultCodeEnum.SUCCESS) ;
    }
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @PutMapping("/update")
    public Result updateById(@RequestBody SysMenu sysMenu) {
        sysMenuService.updateById(sysMenu);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id) {
        sysMenuService.removeById(id);
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
}
