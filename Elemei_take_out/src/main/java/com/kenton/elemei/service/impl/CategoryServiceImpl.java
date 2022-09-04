package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.common.CustomException;
import com.kenton.elemei.entity.Category;
import com.kenton.elemei.entity.Dish;
import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.mapper.CategoryMapper;
import com.kenton.elemei.service.CategoryService;
import com.kenton.elemei.service.DishService;
import com.kenton.elemei.service.SetmealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/3 21:20
 */

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Resource
    private DishService dishService;

    @Resource
    private SetmealService setmealService;

    /**
     * 根据id删除分类 删除之前进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        long countFirst = dishService.count(dishLambdaQueryWrapper);
        // 查询当前分类是否关联了菜品 如果已经关联 抛出一个业务异常
        if(countFirst > 0){
            // 已经关联菜品 抛出一个异常类
            throw new CustomException("当前分类下关联了菜品,不能删除");
        }
        // 查询当前分类是否关联了套餐 如果已经关联 抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 添加查询条件 根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        long countSecond = setmealService.count(setmealLambdaQueryWrapper);
        if (countSecond > 0){
            // 已经关联套餐 抛出一个业务异常
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }
        // 正常删除分类
        super.removeById(id);
    }
}
