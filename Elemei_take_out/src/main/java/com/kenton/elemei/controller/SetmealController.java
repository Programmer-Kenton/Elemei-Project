package com.kenton.elemei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kenton.elemei.common.R;
import com.kenton.elemei.dto.SetmealDto;
import com.kenton.elemei.entity.Category;
import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.service.CategoryService;
import com.kenton.elemei.service.SetmealDishService;
import com.kenton.elemei.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: Kenton
 * @description 套餐管理
 * @date: 2022/9/4 20:12
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Resource
    private SetmealService setmealService;

    @Resource
    private SetmealDishService setmealDishService;

    @Resource
    private CategoryService categoryService;
    /**
     * 新增套餐
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info("套餐信息:{}",setmealDto);

        setmealService.saveWithDish(setmealDto);

        return R.success("新增套餐成功");
    }

    /**
     * 套餐分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        // 构造分页构造器
        Page<Setmeal> pageInfo = new Page<>();
        Page<SetmealDto> dtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();

        // 添加查询条件 根据name进行like模糊查询
        queryWrapper.like(name != null,Setmeal::getName,name);
        // 添加排序条件 根据更新时间降序排序
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        setmealService.page(pageInfo,queryWrapper);

        // 对象拷贝 pageInfo拷贝到dtoPage
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list =  records.stream().map((item) ->{
            SetmealDto setmealDto = new SetmealDto();
            // 对象拷贝
            BeanUtils.copyProperties(item,setmealDto);
            // 分类id
            Long categoryId = item.getCategoryId();
            // 根据分类id查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                // 分类名称
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        setmealService.removeWithDish(ids);
        return R.success("套餐数据删除成功");
    }

    /**
     * 根据条件查询套餐数据
     * @param setmeal
     * @return
     */
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null,Setmeal::getCategoryId,setmeal.getCategoryId());
        queryWrapper.eq(setmeal.getStatus() != null,Setmeal::getCategoryId,setmeal.getStatus());
        queryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(queryWrapper);

        return R.success(list);
    }
}
