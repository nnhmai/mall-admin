package com.hqu.spzx.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.manager.mapper.SysUserMapper;
import com.hqu.spzx.manager.service.SysUserService;
import com.hqu.spzx.model.dto.system.AssginRoleDto;
import com.hqu.spzx.model.dto.system.SysUserDto;
import com.hqu.spzx.model.entity.system.SysUser;
import jakarta.annotation.Resource;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Resource
    private SqlSessionFactory sqlSessionFactory;
    @Override
    public PageInfo<SysUser> findByPage(SysUserDto sysUserDto, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> users = sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(users);
        return sysUserPageInfo;
    }

    @Override
    public int saveSysUser(SysUser sysUser) {
        return sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public int updateSysUser(SysUser sysUser) {
        return sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public int deleteById(Long userId) {
        return sysUserMapper.deleteById(userId);
    }
    @Transactional
    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {
        sysUserMapper.deleteAssign(assginRoleDto.getUserId());
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        SysUserMapper mapper = sqlSession.getMapper(SysUserMapper.class);
        assginRoleDto.getRoleIdList().forEach(roleId->mapper.addAssign(assginRoleDto.getUserId(), roleId));
        sqlSession.commit();
        sqlSession.close();
    }
}
