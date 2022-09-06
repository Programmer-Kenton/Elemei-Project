package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.entity.User;
import com.kenton.elemei.mapper.UserMapper;
import com.kenton.elemei.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/5 9:40
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
