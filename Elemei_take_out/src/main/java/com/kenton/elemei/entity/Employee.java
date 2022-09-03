package com.kenton.elemei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author: Kenton
 * @description 员工实体类
 * @date: 2022/9/3 10:04
 */

@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String name;

    private String password;

    private String phone;

    private String sex;

    private String idNumber;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    //该注解用于标识非主键的字段。将数据库列与 JavaBean 中的属性进行映射
    /*
      DEFAULT	默认不处理
      INSERT	插入时填充字段
      UPDATE	更新时填充字段
      INSERT_UPDATE	插入和更新时填充字段

     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    // create_time和update_time两个字段就可以自动填充为“当前时间”了；
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
