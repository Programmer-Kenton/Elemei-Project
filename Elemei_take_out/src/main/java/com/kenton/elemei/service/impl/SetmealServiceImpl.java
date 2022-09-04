package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.mapper.SetmealMapper;
import com.kenton.elemei.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/3 22:35
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
