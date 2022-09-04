package com.kenton.elemei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 * @author: Kenton
 * @description 菜品口味
 * @date: 2022/9/4 11:05
 */
@Data
public class DishFlavor implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 菜品id
    private Long dishId;

    // 口味名称
    private String name;

    // 口味数据list
    private String value;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    // 是否删除
    private Integer isDeleted;
}
