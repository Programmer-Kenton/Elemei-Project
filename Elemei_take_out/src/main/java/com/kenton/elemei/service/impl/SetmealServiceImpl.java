package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.common.CustomException;
import com.kenton.elemei.dto.SetmealDto;
import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.entity.SetmealDish;
import com.kenton.elemei.mapper.SetmealMapper;
import com.kenton.elemei.service.SetmealDishService;
import com.kenton.elemei.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/3 22:35
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Resource
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐 同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        // 保存套餐的基本信息 操作setmeal 执行insert操作
        this.save(setmealDto);

        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        // 给setmealId赋值
        setmealDishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        // 保存套餐和菜品的关联信息 操作setmeal_dish 执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐 同时删除套餐和菜品的关联数据
     * @param ids
     */
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {

        // 查询商品的状态 确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);

        long count = this.count(queryWrapper);
        if (count > 0) {
            // 如果不能删除 抛出一个业务异常
            throw new CustomException("套餐正在被售卖中,不能删除");
        }

        // 如果可以删除 先删除套餐表中的数据---setmeal
        this.removeByIds(ids);

        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId,ids);

        // 删除关系表中的数据---setmeal_dish
        setmealDishService.remove(lambdaQueryWrapper);
    }
}
