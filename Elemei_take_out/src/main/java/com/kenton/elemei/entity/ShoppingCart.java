package com.kenton.elemei.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: Kenton
 * @description 购物车
 * @date: 2022/9/6 10:22
 */
@Data
public class ShoppingCart implements Serializable {

    private static final long seriaVersionUID = 1L;

    private Long id;

    // 名称
    private String name;

    // 用户id
    private Long userId;

    // 菜品id
    private Long dishId;

    // 套餐id
    private Long setmealId;

    // 口味
    private String dishFlavor;

    // 数量
    private Integer number;

    // 金额
    private BigDecimal amount;

    // 图片
    private String image;

    // 创建时间
    private LocalDateTime createTime;
}
