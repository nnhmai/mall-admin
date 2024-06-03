package com.hqu.spzx.commonservice.exception;

import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

@Data
public class MyException extends Exception{
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;
    public MyException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum=resultCodeEnum;
        this.code=resultCodeEnum.getCode();
        this.message=resultCodeEnum.getMessage();
    }
}
