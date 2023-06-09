package com.atguigu.system.controller;

import com.alibaba.fastjson.JSON;
import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.MD5;
import com.atguigu.model.system.SysUser;
import com.atguigu.model.vo.LoginVo;
import com.atguigu.system.exception.GuiguException;
import com.atguigu.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Resource
    SysUserService sysUserService;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 登录
     * @return
     */
    @ApiOperation(value ="登录")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo){
        SysUser user = sysUserService.getByUsername(loginVo.getUsername());
        //判断用户是否为null
        if(user==null){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_ERROR);
        }
        //判断密码是否错误
        if(!MD5.encrypt(loginVo.getPassword()).equals(user.getPassword())){
            throw new GuiguException(ResultCodeEnum.PASSWORD_ERROR);
        }
        //判断状态是否不为1
        if(user.getStatus().intValue()!=1){
            throw new GuiguException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        //存入reids
        redisTemplate.boundValueOps(token).set(user,2, TimeUnit.HOURS);
        //创建map,用于传回token数据
        Map<String, Object> map = new HashMap();
        //存入token消息
        map.put("token",token);
        return Result.ok(map);
    }

    /**
     * 获取用户信息
     * @return
     */
    @ApiOperation(value = "获取用户信息")
    @GetMapping("/info")
    public Result info(HttpServletRequest request){
    //获取请求头中的token
        String token = request.getHeader("token");
     //获取redis中的用户信息
        SysUser sysUser = (SysUser) redisTemplate.boundValueOps(token).get();
        //调用SysUserService中根据用户id获取用户信息以及权限信息
        Map<String,Object> map = sysUserService.getUserInfoByUser(sysUser);
        return Result.ok(map);
    }

    /**
     * 登出
     * @return
     */
    @ApiOperation(value = "登出")
    @PostMapping("/logout")
    public Result logout(HttpServletRequest request){
         //获取请求头中的token
        String token = request.getHeader("token");
        //删除Redis中的用户信息
        redisTemplate.delete(token);
        return Result.ok();
    }
}
