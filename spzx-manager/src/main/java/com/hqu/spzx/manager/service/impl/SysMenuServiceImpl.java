package com.hqu.spzx.manager.service.impl;

import com.hqu.spzx.commonUtils.AuthContextUtil;
import com.hqu.spzx.commonUtils.BuildTreeNode;
import com.hqu.spzx.commonlogs.Enum.OperatorType;
import com.hqu.spzx.commonlogs.annotation.Log;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.manager.mapper.SysMenuMapper;
import com.hqu.spzx.manager.mapper.SysRoleMenuMapper;
import com.hqu.spzx.manager.mapper.SysUserMapper;
import com.hqu.spzx.manager.service.SysMenuService;
import com.hqu.spzx.manager.service.SysRoleMenuService;
import com.hqu.spzx.model.entity.system.SysMenu;
import com.hqu.spzx.model.entity.system.SysUser;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.system.SysMenuVo;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    @Log(title = "找到所有菜单",operatorType = OperatorType.MANAGER,type = 1)
    public List<SysMenu> findAllNodes() {
        List<SysMenu> allNodes = sysMenuMapper.findAllNodes();
        List<SysMenu> sysMenus = BuildTreeNode.buildTree(allNodes);
        return sysMenus;
    }

    @Override
    public int save(SysMenu sysMenu) {
        return sysMenuMapper.save(sysMenu);
    }

    @Override
    public int updateById(SysMenu sysMenu) {
        return sysMenuMapper.updateById(sysMenu);
    }

    @SneakyThrows
    @Override
    public int removeById(Long id) {
        if(sysMenuMapper.findByParentIdSysMenus(id).size()>0){
            throw new MyException(ResultCodeEnum.NODE_ERROR);
        }
        return sysMenuMapper.deleteById(id);
    }

    @Override
    public List<SysMenuVo> findMenuList() {
        SysUser sysUser = AuthContextUtil.get();
        List<SysMenu> sysMenuList=sysMenuMapper.findMenuById(sysUser.getId());
        List<SysMenu> sysMenus = BuildTreeNode.buildTree(sysMenuList);
        return buildChildren(sysMenus);
    }
    public List<SysMenuVo> buildChildren(List<SysMenu> sysMenus){
        List<SysMenuVo> sysMenuVos=new ArrayList<>();
        for (SysMenu sysMenu : sysMenus) {
            SysMenuVo sysMenuVo=new SysMenuVo();
            sysMenuVo.setName(sysMenu.getComponent());
            sysMenuVo.setTitle(sysMenu.getTitle());
            List<SysMenu> children = sysMenu.getChildren();
            if(!CollectionUtils.isEmpty(children)){
                sysMenuVo.setChildren(buildChildren(children));
            }
            sysMenuVos.add(sysMenuVo);
        }
        return sysMenuVos;
    }

}
