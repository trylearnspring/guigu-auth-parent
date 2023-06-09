package com.atguigu.system.service.impl;

import com.atguigu.common.helper.MenuHelper;
import com.atguigu.common.helper.RouterHelper;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.RouterVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.mapper.SysMenuMapper;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.mapper.SysUserMapper;
import com.atguigu.system.mapper.SysUserRoleMapper;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Resource
    SysRoleMapper sysRoleMapper;

    @Resource
    SysMenuMapper sysMenuMapper;

    @Resource
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysUser> findPage(IPage<SysUser> page, SysUserQueryVo sysUserQueryVo) {
        return sysUserMapper.findPage(page,sysUserQueryVo);
    }

    /**
     * 改变用户状态
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(Long id, Integer status) {
         sysUserMapper.updateStatus(id,status);
    }

    /**
     * 通过用户id获取该用户的所有角色
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> getRoleById(Long userId) {
        //查询所有角色
        List<SysRole> allRoles = sysRoleMapper.selectList(null);
        //根据userId查询该用户所有的信息
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",userId);
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
        //获取该用户的角色信息集合
        ArrayList<Long> roleIds = new ArrayList<>();
        for (SysUserRole userRole : userRoles) {
            //同一个用户有多个roleId
            Long roleId = userRole.getRoleId();
            roleIds.add(roleId);
        }
        //将allRoles和roleIds存入map返回
        HashMap<String, Object> map = new HashMap<>();
        map.put("allRoles",allRoles);
        map.put("roleIds",roleIds);
        return map;
    }

    /**
     * 分配角色权限
     * @param assignRoleVo
     */
    @Transactional(readOnly = false)
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //根据userId删除原来分配的角色
        QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
        sysUserRoleQueryWrapper.eq("user_id",assignRoleVo.getUserId());
        sysUserRoleMapper.delete(sysUserRoleQueryWrapper);
        //添加新的roleId给userId
        List<Long> roleIdList = assignRoleVo.getRoleIdList();
        for (Long roleId : roleIdList) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleMapper.insert(sysUserRole);
        }
    }

    /**
     * 通过用户名获取用户
     * @param userName
     * @return
     */
    @Override
    public SysUser getByUsername(String userName) {
        SysUser sysuser = sysUserMapper.selectOne(new QueryWrapper<SysUser>().eq("username", userName));
        return sysuser;
    }


    /**
     * 通过用户id查询用户信息,包括权限
     * @param sysUser
     * @return
     *
     *     map.put(" name ", sysUser.getName ());
     *         //当前权限控制使用不到，我们暂时忽略
     *         map.put("roles",new HashSet<>());
     *         map.put("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
     *         map.put("routers", routerVoList);
     *         map.put("buttons", buttonPermissons);
     */
    @Override
    public Map<String, Object> getUserInfoByUser(SysUser sysUser) {
        //获取该用户的所有菜单
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuListByUserId(sysUser.getId());
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",sysUser.getName());
        //当前权限控制使用不到，我们暂时忽略
        map.put("roles",new HashSet<>());
        map.put("avatar",sysUser.getHeadUrl());
        map.put("routers", getRouterVoList(sysMenuList));
        map.put("buttons", getUserBtnPermsByMenus(sysMenuList));
        return map;
    }

    /**
     * 将该菜单转换为路由
     * @param sysMenuList
     * @return
     */
    public List<RouterVo> getRouterVoList(List<SysMenu> sysMenuList) {
        //转换为菜单树
        List<SysMenu> sysMenuTree = MenuHelper.buildTree(sysMenuList);
        //转换为路由
        List<RouterVo> routerVos = RouterHelper.buildRouters(sysMenuTree);
        return routerVos;
    }


    /**
     * 获取该菜单中所有按钮的标识权限
     * @param sysMenus
     * @return
     */
    @Override
    public List<String> getUserBtnPermsByMenus(List<SysMenu> sysMenus) {
        List<String> btnPerms = new ArrayList<>();
        //获取该菜单集合中所有按钮
        for (SysMenu sysMenu : sysMenus) {
            if(sysMenu.getType()==2){
                btnPerms.add(sysMenu.getPerms());
            }
        }
        return btnPerms;
    }
}
