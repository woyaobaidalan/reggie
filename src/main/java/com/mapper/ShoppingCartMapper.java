package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.domain.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
