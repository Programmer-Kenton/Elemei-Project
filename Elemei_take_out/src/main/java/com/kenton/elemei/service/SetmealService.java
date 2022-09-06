package com.kenton.elemei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenton.elemei.dto.SetmealDto;
import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.entity.SetmealDish;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    /**
     * 新增套餐 同时需要保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);

    /**
     * 删除套餐 同时删除套餐和菜品的关联数据
     * @param ids
     */
    void removeWithDish(List<Long> ids);
}
