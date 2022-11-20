package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersDetailMapper extends BaseMapper<OrderDetail> {
}
