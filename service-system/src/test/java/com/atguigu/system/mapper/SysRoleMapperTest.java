package com.atguigu.system.mapper;

import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysRoleMapperTest {

    @Resource
    SysRoleMapper sysRoleMapper;

    @Test
    public void selectTest(){
        Integer integer = sysRoleMapper.selectCount(null);
        System.out.println("integer = " + integer);
        List<SysRole> sysRoles = sysRoleMapper.selectByMap(null);
        System.out.println("sysRoles = " + sysRoles);
    }

    @Test
    public void deleteTest(){
        int i = sysRoleMapper.deleteById(9);

    }

    @Test
    void findPage() {
        SysRoleQueryVo sysRoleQueryVo = new SysRoleQueryVo();
        sysRoleQueryVo.setRoleName("管理员");
        IPage<SysRole> page = sysRoleMapper.findPage(new Page<SysRole>(1, 10), sysRoleQueryVo);
        System.out.println("page = " + page);
    }
}