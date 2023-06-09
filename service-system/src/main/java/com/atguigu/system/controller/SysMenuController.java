package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysMenu;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.AssignMenuVo;
import com.atguigu.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.api.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    SysMenuService sysMenuService;

    @ApiOperation(value = "查询所有根节点")
    @GetMapping("/findRootNodes")
    public Result findRootNodes(){
        List<SysMenu> rootMenuList=sysMenuService.findRootNodes();
        return Result.ok(rootMenuList);
    }


    @ApiOperation(value = "新增菜单")
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu menu){
        sysMenuService.save(menu);
        return Result.ok();
    }

    /**
     * 删除没有子节点的菜单,重写方法
     * @param id
     * @return
     */
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id){
        sysMenuService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysMenu menu){
        sysMenuService.updateById(menu);
        return Result.ok();
    }

    @ApiOperation(value = "查询菜单")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        SysMenu menu = sysMenuService.getById(id);
        return Result.ok(menu);
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> menuList=sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(menuList);
    }

    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignMenuVo assignMenuVo){
        sysMenuService.doAssign(assignMenuVo);
        return Result.ok();
    }
}
