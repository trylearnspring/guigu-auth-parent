package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.mapper.SysRoleMapper;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Resource
    SysRoleMapper sysRoleMapper;

    /**
     * 分页查询
     * @param page
     * @param sysRoleQueryVo
     * @return
     */
    @Override
    public IPage<SysRole> findPage(IPage<SysRole> page, SysRoleQueryVo sysRoleQueryVo) {
        return sysRoleMapper.findPage(page,sysRoleQueryVo);
    }
}
