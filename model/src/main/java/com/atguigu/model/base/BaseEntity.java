package com.atguigu.model.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体类中公共的属性
 *
 * 注意:强制使用包装类
 */
@Data
public class BaseEntity implements Serializable{


    //唯一标识

      /**IdType枚举类
     * IdType.AUTO 主键自动增加。利用MySQL数据库的auto_increment来分配主键的。
     * IdType.ASSIGN_ID 通过雪花算法生成19位长度数值。1663442927768150018  前端支持最大长度整数16位。
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    //创建时间
    @TableField(value = "create_time")
    private Date createTime;

    //更新时间
    @TableField(value = "update_time")
    private Date updateTime;

    //物理删除，真正意义上的从数据库中移除
    //逻辑删除，并未从数据库中移除，用delete删除数据库数据时，不会真正删除数据库数据，只会改变该属性的值（变0为1），变成1后就查询不到该属性
    @TableLogic//　　　　value = “” 未删除的值，默认值为0 delval = “” 删除后的值，默认值为1
    @TableField(value = "is_deleted")
    private Integer isDelete;

    @TableField(exist = false)
    private Map<String,Object> param=new HashMap<>();
}
