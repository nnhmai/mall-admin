package com.hqu.spzx.commonlogs.annotation;

import com.hqu.spzx.commonlogs.Enum.OperatorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    public String title();
    public OperatorType operatorType();
    public boolean isSaveParam() default true;
    public boolean isSaveResult() default true;
    public int type();
}
