package com.hqu.spzx.commonUtils;

import com.hqu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class BuildTreeNode {
    public static List<SysMenu> buildTree(List<SysMenu> list){
        System.out.println(list);
        List<SysMenu>list1=new ArrayList<>();
        for (SysMenu sysMenu : list) {
            if(sysMenu.getParentId().longValue()==0){
                list1.add(addChildren(sysMenu,list));
            }
        }
        return list1;
    }
    private static SysMenu addChildren(SysMenu sysMenu,List<SysMenu> list){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for (SysMenu menu : list) {
            if(menu.getParentId().longValue()==sysMenu.getId().longValue()){
                sysMenu.getChildren().add(addChildren(menu,list));
            }
        }
        return sysMenu;
    }
}
