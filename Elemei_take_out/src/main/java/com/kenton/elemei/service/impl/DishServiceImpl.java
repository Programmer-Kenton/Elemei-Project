package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.dto.DishDto;
import com.kenton.elemei.entity.Dish;
import com.kenton.elemei.entity.DishFlavor;
import com.kenton.elemei.mapper.DishMapper;
import com.kenton.elemei.service.DishFlavorService;
import com.kenton.elemei.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/3 22:32
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    // 注入操作菜品口味表的服务层接口 利用其实现类完成数据保存操作
    @Resource
    private DishFlavorService dishFlavorService;

    /**
     * 新增菜品 同时保存对应的口味数据
     * @param dishDto
     */
    // 开启数据表事务
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {
        // 保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        // 获得保存在菜品表菜品的id
        Long dishId = dishDto.getId();

        // 获得网页传来的菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        // 保存菜品口味数据到菜品口味表dish_flavor
        // saveBatch批量保存
        dishFlavorService.saveBatch(flavors);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

        // 添加根据id查询菜品信息的方法 用于菜品修改
        Dish dish = this.getById(id);

        // 拷贝对象属性
        DishDto dishDto = new DishDto();
        // 左边拷贝到右边
        BeanUtils.copyProperties(dish,dishDto);

        // 查询当前菜品对应的口味信息 从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }
}
