package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.entity.ShoppingCart;
import com.kenton.elemei.mapper.ShoppingCartMapper;
import com.kenton.elemei.service.ShoppingCartService;
import org.springframework.stereotype.Service;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/6 10:28
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
