package com.atguigu.system.service.impl;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysRoleServiceImplTest {

    @Autowired
    SysRoleService sysRoleService;

    @Test
    public void selectTest(){
        int count = sysRoleService.count(null);
        System.out.println("count = " + count);
        List<SysRole> list = sysRoleService.list();
        System.out.println("list = " + list);

    }

    @Test
    void findPage() {
        Page<SysRole> page=new Page<>(1,10);
        SysRoleQueryVo sysRoleQueryVo = new SysRoleQueryVo();
        sysRoleQueryVo.setRoleName("普通");
        sysRoleService.findPage(page,sysRoleQueryVo);
    }

    @Test
    void test() {
        Long f=12L;
        System.out.println(f.longValue());
    }
}