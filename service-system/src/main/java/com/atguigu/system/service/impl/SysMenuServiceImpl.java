package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRoleMenu;
import com.atguigu.model.vo.AssignMenuVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMenuMapper;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Resource
    SysMenuMapper sysMenuMapper;

    @Resource
    SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 查询所有菜单,按照树结构
     * @return
     */
    @Override
    public List<SysMenu> findRootNodes() {
        //查询所有菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectList(null);
        //利用MenuHelper工具类按父子分出层级
        List<SysMenu> rootMenuList = MenuHelper.buildTree(sysMenuList);
        return rootMenuList;
    }

    /**
     * 删除没有子节点的菜单
     * @param id 主键ID
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //以该菜单id为父id查询其子菜单数量
        int count = count(new QueryWrapper<SysMenu>().eq("parent_id", id));
        //判断
        if(count>0){
            //说明有子节点菜单
            throw new GuiguException(ResultCodeEnum.NODE_ERROR);
        }
        //没有子节点
        sysMenuMapper.deleteById(id);
        return true;
    }

    /**
     * 通过角色id查找该角色的所有菜单
     * @param roleId
     * @return
     */
    @Override
    public List<SysMenu> findSysMenuByRoleId(Long roleId) {
        //获取所有状态为1的权限
        List<SysMenu> allMenuList = sysMenuMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        //根据roleId获取该角色所有的权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        ArrayList<Long> menuIdList = new ArrayList<>();
        for (SysRoleMenu sysRoleMenu : roleMenuList) {
            menuIdList.add(sysRoleMenu.getMenuId());
        }
        for (SysMenu menu : allMenuList) {
            if(menuIdList.contains(menu.getId())){
                //该权限已经被分配
                menu.setSelect(true);
            }else{
                menu.setSelect(false);
            }
        }
        List<SysMenu> sysMenuList = MenuHelper.buildTree(allMenuList);
        return sysMenuList;
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
         //删除所有已分配的权限
         sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id", assignMenuVo.getRoleId()));
         //遍历assignMenuVo中的menuList
        for (Long menuId : assignMenuVo.getMenuIdList()) {
               if(menuId!=null){
                   SysRoleMenu sysRoleMenu = new SysRoleMenu();
                   sysRoleMenu.setMenuId(menuId);
                   sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
                   sysRoleMenuMapper.insert(sysRoleMenu);
               }
        }
    }
}
