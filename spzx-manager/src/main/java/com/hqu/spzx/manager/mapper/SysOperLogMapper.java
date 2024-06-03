package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.system.SysOperLog;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Async;

@Mapper
public interface SysOperLogMapper {
    void saveLog(SysOperLog sysOperLog);
}
