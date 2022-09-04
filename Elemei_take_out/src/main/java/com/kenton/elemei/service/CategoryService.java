package com.kenton.elemei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenton.elemei.entity.Category;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
