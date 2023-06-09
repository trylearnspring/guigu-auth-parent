package com.atguigu.common.helper;

import com.atguigu.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    /**
     * 分出根节点
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        //创建根节点数组
        List<SysMenu> rootNodes=new ArrayList<>();
        //parent_id=0的节点就是根节点
        for (SysMenu menu : sysMenuList) {
            if(menu.getParentId().longValue()==0){ //menu.getParentId()返回的是Long型,需要用longValue()方法转化为Integer
                 rootNodes.add(findChildren(menu,sysMenuList));
            }
        }
       return rootNodes;
    }


    /**
     *传入父节点,找到子节点
     * @param menu
     * @param sysMenuList
     * @return
     */
    private static SysMenu findChildren(SysMenu menu, List<SysMenu> sysMenuList) {
        //创建子节点集合
         List<SysMenu> childrenNodes=new ArrayList<>();
        for (SysMenu sysMenu : sysMenuList) {
        //遍历判断
            if(sysMenu.getParentId().longValue()==menu.getId().longValue()){ //用longValue转换为基础数据类型,再用==
                //如果该元素的parent_id和所传入的父菜单的id相等,就说明找到了子菜单
                //递归
                childrenNodes.add(findChildren(sysMenu,sysMenuList));
            }
        }
        menu.setChildren(childrenNodes);
        return menu;
    }
}
