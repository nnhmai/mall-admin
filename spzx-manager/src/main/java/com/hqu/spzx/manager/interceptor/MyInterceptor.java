package com.hqu.spzx.manager.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.hqu.spzx.commonUtils.AuthContextUtil;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        if("OPTIONS".equals(method)){
            return true;
        }
        String token = request.getHeader("token");
        String redis = redisTemplate.opsForValue().get("user:login:" + token);
        if(StrUtil.isEmpty(token)||StrUtil.isEmpty(redis)){
            responseNoLoginInfo(response);
            return false;
        }
        redisTemplate.expire("user:login:" + token,30,TimeUnit.MINUTES);
        AuthContextUtil.set(JSON.parseObject(redis, SysUser.class));
        return true;
    }
    //响应208状态码给前端
    private void responseNoLoginInfo(HttpServletResponse response) {
        Result<Object> result = Result.build(null, ResultCodeEnum.LOGIN_AUTH);
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(JSON.toJSONString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AuthContextUtil.remove();
    }
}
