package com.atguigu.system.mapper;

import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.SysUserQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysUserMapperTest {

    @Autowired
    SysUserMapper sysUserMapper;

    @Test
    void findPage() {
        Page<SysUser> sysUserPage = new Page<>(1,2);
        SysUserQueryVo sysUserQueryVo = new SysUserQueryVo();
        sysUserQueryVo.setKeyword("ad");
        sysUserMapper.findPage(sysUserPage,sysUserQueryVo);
    }
}