package com.kenton.elemei.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author: Kenton
 * @description 分类实体
 * @date: 2022/9/3 21:13
 */
@Data
// 新增菜品分类 和 新增套餐分类
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 类型 1 菜品分类 2套餐分类
    private Integer type;

    // 分类名称
    private String name;

    // 顺序
    private Integer sort;

    // 创建时间
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    // 更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 创建人
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    // 修改人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
