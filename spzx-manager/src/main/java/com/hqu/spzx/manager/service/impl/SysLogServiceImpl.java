package com.hqu.spzx.manager.service.impl;


import com.hqu.spzx.manager.mapper.SysOperLogMapper;
import com.hqu.spzx.commonlogs.service.SysLogService;
import com.hqu.spzx.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class SysLogServiceImpl implements SysLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;
    @Override
    @Async
    public void save(SysOperLog sysOperLog) {
        sysOperLogMapper.saveLog(sysOperLog);
    }
}
