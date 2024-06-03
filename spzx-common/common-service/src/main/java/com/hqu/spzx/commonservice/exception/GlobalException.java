package com.hqu.spzx.commonservice.exception;

import com.hqu.spzx.model.vo.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(MyException.class)
    public Result myExceptionHandler(MyException e){
        return Result.build(null,e.getResultCodeEnum());
    }
}
