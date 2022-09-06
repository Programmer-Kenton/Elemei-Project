package com.kenton.elemei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kenton.elemei.entity.Orders;

public interface OrderService extends IService<Orders> {

    /**
     * 用户下单
     * @param orders
     */
    void submit(Orders orders);
}
