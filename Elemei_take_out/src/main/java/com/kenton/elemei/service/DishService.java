package com.kenton.elemei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenton.elemei.dto.DishDto;
import com.kenton.elemei.entity.Dish;

/**
 * @author: Kenton
 * @description 菜品服务层
 * @date: 2022/9/3 22:31
 */
public interface DishService extends IService<Dish> {

    // 选购菜品的同时插入菜品对应的口味数据 需要操作两张表:dish、dish_flavor
    public void saveWithFlavor(DishDto dishDto);

    // 添加根据id查询菜品信息的方法 用于菜品修改
    DishDto getByIdWithFlavor(Long id);
}
