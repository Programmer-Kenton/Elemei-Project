package com.kenton.elemei.dto;

import com.kenton.elemei.entity.Setmeal;
import com.kenton.elemei.entity.SetmealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Kenton
 * @description 套餐
 * @date: 2022/9/4 20:04
 */
@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
