package com.kenton.elemei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kenton.elemei.entity.OrderDetail;
import com.kenton.elemei.mapper.OrderDetailMapper;
import com.kenton.elemei.mapper.OrderMapper;
import com.kenton.elemei.service.OrderDetailService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * @author: Kenton
 * @description
 * @date: 2022/9/6 11:47
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
