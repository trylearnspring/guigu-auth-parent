package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.AssignRoleVo;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.model.vo.SysUserQueryVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.jndi.cosnaming.CNCtx;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

     @Autowired
     SysUserService sysUserService;

    @ApiOperation(value = "用户的分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<IPage<SysUser>> findPage(
            @ApiParam(name = "pageNum",value = "页码",required = true) @PathVariable Long pageNum,
            @ApiParam(name = "pageSize",value = "页面数据条数",required = true) @PathVariable Long pageSize,
            SysUserQueryVo sysUserQueryVo){
        Page<SysUser> page=new Page<>(pageNum,pageSize);
        IPage<SysUser> resultPage= null;
        try {
            resultPage = sysUserService.findPage(page,sysUserQueryVo);
        } catch (Exception e) {
            throw new GuiguException(ResultCodeEnum.FAIL.getCode(),e.getMessage());
        }
        return Result.ok(resultPage);
    }

    @ApiOperation(value ="获取用户")
    @GetMapping("/getById/{id}")
    public Result getById(@PathVariable Long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }

    @ApiOperation(value = "新增用户")
    @PostMapping("/save")
    public Result save(@RequestBody SysUser user){
        sysUserService.save(user);
        return Result.ok();
    }

    @ApiOperation(value = "修改用户")
    @PutMapping("/updateById")
    public Result updateById(@RequestBody SysUser user){
        sysUserService.updateById(user);
        return Result.ok();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id){
        sysUserService.removeById(id);
        return Result.ok();
    }

    @ApiOperation(value = "更改用户状态")
    @PutMapping("/updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id, @PathVariable Integer status){
        sysUserService.updateStatus(id,status);
        return Result.ok();
    }

    @ApiOperation(value = "查询用户的角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> map=sysUserService.getRoleById(userId);
        return Result.ok(map);
    }

    @ApiOperation(value = "用户的角色分配")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssignRoleVo assignRoleVo){
        sysUserService.doAssign(assignRoleVo);
        return Result.ok();
    }
}
