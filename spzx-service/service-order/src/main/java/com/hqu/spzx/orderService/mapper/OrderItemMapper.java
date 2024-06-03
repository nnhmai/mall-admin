package com.hqu.spzx.orderService.mapper;

import com.hqu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    void batchInsert(@Param("itemList") List<OrderItem> itemList, @Param("orderId") Long orderId);


    List<OrderItem> findByOrderId(Long id);
}
