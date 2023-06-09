package com.atguigu.system.service;

import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface SysUserService extends IService<SysUser> {
    IPage<SysUser> findPage(IPage<SysUser> page, SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long id, Integer status);

    Map<String, Object> getRoleById(Long userId);

    void doAssign(AssignRoleVo assignRoleVo);

    SysUser getByUsername(String userName);

    Map<String, Object> getUserInfoByUser(SysUser sysUser);

    List<String> getUserBtnPermsByMenus(List<SysMenu> sysMenu);
}
