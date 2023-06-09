package com.atguigu.system.handler;

import com.atguigu.common.result.Result;
import com.atguigu.system.exception.GuiguException;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 */
@ControllerAdvice//提前约束
//ControllerAdvice就是Controller的Advice，即Controller的强化或者横切，
//说的更确切一些就是其他Controller在执行之前，一定会先执行配置了ControllerAdvice的Controller。
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)//监控所有Controller，任意Controller产生异常，都会进到这里
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    /**
     * 特定异常处理:算数异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result error(ArithmeticException e){
       return Result.fail().message(e.getMessage());
    }

    /**
     * 特定异常处理：自定义异常
     * @param e
     * @return
     */
    @ExceptionHandler
    @ResponseBody
    public Result error(GuiguException e){return Result.fail().message(e.getMessage()).code(e.getCode());}

//    /**
//     *对全局的Controller接收日期对象的时间格式进行处理
//     * @param binder
//     */
//    @InitBinder
//    public void timeFormat(WebDataBinder binder){
//        binder.addCustomFormatter(new DateFormatter("yyyy-MM-dd"));
//    }

}