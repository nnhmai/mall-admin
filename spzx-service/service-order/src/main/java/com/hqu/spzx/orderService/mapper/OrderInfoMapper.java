package com.hqu.spzx.orderService.mapper;

import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.entity.order.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderInfoMapper {

    void save(OrderInfo orderInfo);

    OrderInfo findById(Long orderId);

    List<OrderInfo> findByStatus(@Param("userId") Long userId,@Param("orderStatus") Integer orderStatus);

    OrderInfo findByOrderNo(String orderNo);
}
