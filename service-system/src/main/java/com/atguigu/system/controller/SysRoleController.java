package com.atguigu.system.controller;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.vo.SysRoleQueryVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysRoleService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    SysRoleService sysRoleService;

    @ApiOperation(value = "获取全部角色信息")
    @GetMapping("/findAll")
    public Result<List<SysRole>> findAll(){
        List<SysRole> sysRoleList = sysRoleService.list();
        Result<List<SysRole>> result = Result.ok(sysRoleList);
        return result;
    }

    @ApiOperation(value = "角色的分页查询")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result<IPage<SysRole>> findPage(
         @ApiParam(name = "pageNum",value = "页码",required = true) @PathVariable Long pageNum,
         @ApiParam(name = "pageSize",value = "页面数据条数",required = true) @PathVariable Long pageSize,
         SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> page=new Page<>(pageNum,pageSize);
        IPage<SysRole> resultPage= null;
        try {
            resultPage = sysRoleService.findPage(page,sysRoleQueryVo);
        } catch (Exception e) {
            throw new GuiguException(ResultCodeEnum.FAIL.getCode(),e.getMessage());
        }
        return Result.ok(resultPage);
    }

   @ApiOperation(value ="获取角色")
   @GetMapping("/getById/{id}")
   public Result getById(@PathVariable Long id){
//       try {
//           System.out.println(1/0);//运行时异常底层是自动往外抛出的，不需要我们手工抛出。
//       } catch (Exception e) {
//           throw new GuiguException(20001,"自定义异常抛出");//可以用手动抛出的方式，达到目的
//       }
       SysRole role = sysRoleService.getById(id);
       return Result.ok(role);
   }

   @ApiOperation(value = "新增角色")
   @PostMapping("/save")
    public Result save(@RequestBody SysRole role){
        sysRoleService.save(role);
        return Result.ok();
   }

   @ApiOperation(value = "修改角色")
   @PutMapping("/updateById")
    public Result updateById(@RequestBody SysRole role){
       sysRoleService.updateById(role);
       return Result.ok();
   }

   @ApiOperation(value = "删除角色")
   @DeleteMapping("/removeById/{id}")
    public Result removeById(@PathVariable Long id){
        sysRoleService.removeById(id);
        return Result.ok();
   }

   @ApiOperation(value = "根据id列表删除")
   @DeleteMapping("/removeByIds")
    public Result removeByIds(@RequestBody List<Long> ids){
        sysRoleService.removeByIds(ids);
        return Result.ok();
   }

}
