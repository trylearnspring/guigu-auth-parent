package com.atguigu.system.mapper;

import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> findPage(IPage<SysUser> page, @Param("vo") SysUserQueryVo sysUserQueryVo);

    void updateStatus(Long id, Integer status);
}
