package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.entity.DishFlavor;
import com.kenton.elemei.mapper.DishFlavorMapper;
import com.kenton.elemei.service.DishFlavorService;
import org.springframework.stereotype.Service;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/4 11:13
 */

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
