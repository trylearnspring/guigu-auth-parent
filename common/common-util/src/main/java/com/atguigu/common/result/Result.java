package com.atguigu.common.result;

import lombok.Data;

/**
 * 全局统一返回结果类
 * @param <T> 返回数据类型
 */
@Data
public class Result<T>{
    //返回码
    private Integer code;
    //返回消息
    private String message;
    //返回数据
    private T data;

    /**
     * 创建Result对象,传入data
     * @param data
     * @return
     * @param <T>
     */
    public static<T> Result<T> build(T data){
        Result<T> result = new Result<>();
        if(null!=data) result.setData(data);
        return result;
    }

    /**统一规范的code和message
     * 从统一返回结果状态信息类中获取code和message
     * 传入resultCodeEnum枚举对象，和返回对象data，获取返回结果类对象Result
     * @param resultCodeEnum
     * @param data
     * @return Result
     * @param <T> data类型
     */
    public static<T> Result<T> build(ResultCodeEnum resultCodeEnum,T data){
        Result<T> result = build(data);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    /**自定义的code和message
     * 传入自定义的状态码和信息，以及返回对象data，获取result对象
     * @param code
     * @param message
     * @param data
     * @return
     * @param <T>
     */
    public static<T> Result<T> build(Integer code,String message,T data){
        Result<T> result = build(data);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 操作成功，用规范中的ResultCodeEnum.SUCCESS枚举
     * @param data
     * @return
     * @param <T>
     */
    public static<T> Result<T> ok(T data){
      return build(ResultCodeEnum.SUCCESS,data);
    }

    /**
     * 操作成功,没有返回码和信息
     * @return
     * @param <T>
     */
    public static<T> Result<T> ok(){
        return ok(null);
    }


    /**
     * 操作失败,用规范中的ResultCodeEnum.FAIL枚举
     * @param data
     * @return
     * @param <T>
     */
    public static<T> Result<T> fail(T data){
        return build(ResultCodeEnum.FAIL,data);
    }

    /**
     * 操作失败，没有返回信息
     * @return
     * @param <T>
     */
    public static<T> Result<T> fail(){
        return fail(null);
    }

    /**
     * 自定义消息
     * @param msg
     * @return
     */
    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    /**
     * 自定义返回码
     * @param code
     * @return
     */
    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }
}
