package com.kenton.elemei.entity;

import lombok.Data;

/**
 * @author: Kenton
 * @description 用户实体类
 * @date: 2022/9/5 9:37
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;

    private Long id;

    // 姓名
    private String name;

    // 手机号
    private String phone;

    // 性别 女0 男1
    private String sex;

    // 身份证号
    private String idNumber;

    // 头像
    private String avatar;

    // 状态 0禁用 1正常
    private Integer status;
}
