package com.kenton.elemei.dto;

import com.kenton.elemei.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Kenton
 * @description 用户类扩展
 * @date: 2022/9/5 10:41
 */
public class UserDto extends User {

    private String returnMsg;

    private List<UserDto> msg;
}
