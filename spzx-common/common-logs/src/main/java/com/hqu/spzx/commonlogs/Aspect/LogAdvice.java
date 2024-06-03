package com.hqu.spzx.commonlogs.Aspect;

import com.alibaba.fastjson2.JSON;
import com.hqu.spzx.commonUtils.AuthContextUtil;
import com.hqu.spzx.commonlogs.annotation.Log;

import com.hqu.spzx.commonlogs.service.SysLogService;
import com.hqu.spzx.model.entity.system.SysOperLog;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LogAdvice {
    @Autowired
    private SysLogService sysLogService;
    @Around(value = "@annotation(log)")
    public Object doLogAdvice(ProceedingJoinPoint joinPoint,Log log){
        SysOperLog sysOperLog=new SysOperLog();
        preSet(joinPoint, log, sysOperLog);
        int status=0;
        Object proceed=null;
        try {
            status=1;
            proceed = joinPoint.proceed();
        } catch (Throwable e) {
            sysOperLog.setErrorMsg(e.getMessage());
            throw new RuntimeException(e);
        }
        sysOperLog.setStatus(status);
        if(log.isSaveResult()){
            sysOperLog.setJsonResult("ok");
        }
        sysLogService.save(sysOperLog);
        return proceed;
    }

    private void preSet(ProceedingJoinPoint joinPoint, Log log, SysOperLog sysOperLog) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        sysOperLog.setMethod(method.getName());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        sysOperLog.setTitle(log.title());
        sysOperLog.setRequestMethod(request.getMethod());
        sysOperLog.setOperIp(request.getRemoteAddr());
        sysOperLog.setOperUrl(request.getRequestURI());
        sysOperLog.setOperName(AuthContextUtil.get().getName());
        if(log.isSaveParam()){
            String requestMethod = sysOperLog.getRequestMethod();
            if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
                String params = Arrays.toString(joinPoint.getArgs());
                sysOperLog.setOperParam(params);
            }
        }
    }
}
