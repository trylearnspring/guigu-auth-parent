package com.atguigu.system.mapper;

import com.atguigu.model.system.SysMenu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SysMenuMapperTest {

    @Resource
    SysMenuMapper sysMenuMapper;

    @Test
    void selectMenuListByUserId() {
        List<SysMenu> sysMenuList = sysMenuMapper.selectMenuListByUserId(1L);
        System.out.println("sysMenuList = " + sysMenuList);
    }
}