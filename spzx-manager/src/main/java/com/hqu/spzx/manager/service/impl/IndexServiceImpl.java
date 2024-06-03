package com.hqu.spzx.manager.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.util.StringUtil;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.manager.mapper.IndexMapper;
import com.hqu.spzx.manager.service.IndexService;
import com.hqu.spzx.model.dto.system.LoginDto;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.system.LoginVo;
import com.hqu.spzx.model.vo.system.ValidateCodeVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class IndexServiceImpl implements IndexService {
    @Autowired
    private IndexMapper indexMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @SneakyThrows
    public LoginVo Login(LoginDto loginDto) {
        SysUser sysUser = indexMapper.selectByUsername(loginDto.getUserName());
        if(null==sysUser){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        String s = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes());
        if(!sysUser.getPassword().equals(s)){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        String redisCode = redisTemplate.opsForValue().get("user:login:validator:"+loginDto.getCodeKey());
        if(StringUtil.isEmpty(redisCode) ||!StrUtil.equalsIgnoreCase(redisCode,loginDto.getCaptcha())){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:"+token, JSON.toJSONString(sysUser),30,TimeUnit.MINUTES);
        LoginVo loginVo=new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public ValidateCodeVo generateValidate() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(300, 100, 4, 4);
        String codeValue = captcha.getCode();
        String imageBase64 = captcha.getImageBase64();
        String codeKey = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("user:login:validator:"+codeKey,codeValue,5, TimeUnit.MINUTES);
        ValidateCodeVo validateCodeVo=new ValidateCodeVo();
        validateCodeVo.setCodeKey(codeKey);
        validateCodeVo.setCodeValue("data:image/png;base64," + imageBase64);
        System.out.println(codeValue);
        return validateCodeVo;
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login:" + token);
    }
}
