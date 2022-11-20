package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.core.annotation.Order;
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
