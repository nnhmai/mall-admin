package com.hqu.spzx.manager.controller;

import com.hqu.spzx.commonUtils.AuthContextUtil;
import com.hqu.spzx.manager.service.IndexService;
import com.hqu.spzx.manager.service.SysMenuService;
import com.hqu.spzx.model.dto.system.LoginDto;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.system.SysMenuVo;
import com.hqu.spzx.model.vo.system.ValidateCodeVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Autowired
    private IndexService indexService;
    @Autowired
    private SysMenuService sysMenuService;

    @Operation(summary = "登录接口")
    @PostMapping("login")
    public Result Login(@RequestBody LoginDto loginDto){
        return Result.build(indexService.Login(loginDto),ResultCodeEnum.SUCCESS);
    }


    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode() {
        ValidateCodeVo validateCodeVo = indexService.generateValidate();
        return Result.build(validateCodeVo , ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/getUserInfo")
    public Result<SysUser> getUserInfo(@RequestHeader(name = "token") String token) {
        return Result.build(AuthContextUtil.get(), ResultCodeEnum.SUCCESS) ;
    }

    @GetMapping(value = "/logout")
    public Result logout(@RequestHeader(value = "token") String token) {
        indexService.logout(token) ;
        return Result.build(null , ResultCodeEnum.SUCCESS) ;
    }
    @GetMapping("/menus")
    public Result menus() {
        List<SysMenuVo> sysMenuVoList =  sysMenuService.findMenuList() ;
        return Result.build(sysMenuVoList , ResultCodeEnum.SUCCESS) ;
    }
}
