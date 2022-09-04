package com.kenton.elemei.dto;

import com.kenton.elemei.entity.Dish;
import com.kenton.elemei.entity.DishFlavor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Kenton
 * @description 封装菜品页面传输的数据
 * DTO 全称Data Transfer Object 即数据传输对象 一般用于展示层与服务层之间的数据传输
 * @date: 2022/9/4 11:39
 */
//@Data注解自动添加get和set方法 尽管Dish的属性定义成私有 但通过反射获取
@Data
// 继承父类拥有父类的属性和方法 子类再自己添加数组功能
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
