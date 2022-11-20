package com.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.domain.OrderDetail;
import com.mapper.OrdersDetailMapper;
import com.mapper.OrdersMapper;
import com.service.OrdersDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrdersDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrdersDetailService {
}
