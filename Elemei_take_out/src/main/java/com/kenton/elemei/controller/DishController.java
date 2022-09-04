package com.kenton.elemei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kenton.elemei.common.R;
import com.kenton.elemei.dto.DishDto;
import com.kenton.elemei.entity.Category;
import com.kenton.elemei.entity.Dish;
import com.kenton.elemei.service.CategoryService;
import com.kenton.elemei.service.DishFlavorService;
import com.kenton.elemei.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Kenton
 * @description 菜品管理
 * @date: 2022/9/4 11:14
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Resource
    private DishService dishService;

    @Resource
    private DishFlavorService dishFlavorService;

    @Resource
    private CategoryService categoryService;

    /**
     * 新增菜品
     * @param dishDto
     * @return
     */
    // 前端页面返回JSON格式数据
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto){

        log.info(dishDto.toString());

        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }

    /**
     * 菜品信息分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){

        // 创建分页构造器
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        // 创建条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        // 添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        // 添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo,queryWrapper);

        // 对象拷贝 Dish拷贝到DishDto
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);
            // 菜品分类id
            Long categoryId = item.getCategoryId();

            // 根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);
        return R.success(pageInfo);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){

        DishDto dishDto = dishService.getByIdWithFlavor(id);

        return R.success(dishDto);
    }
}
