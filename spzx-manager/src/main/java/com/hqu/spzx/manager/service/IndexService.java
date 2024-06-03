package com.hqu.spzx.manager.service;

import com.hqu.spzx.model.dto.system.LoginDto;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.system.LoginVo;
import com.hqu.spzx.model.vo.system.ValidateCodeVo;

public interface IndexService {
     LoginVo Login(LoginDto loginDto);
     ValidateCodeVo generateValidate();

     void logout(String token);
}
