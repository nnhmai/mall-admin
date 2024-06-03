package com.hqu.spzx.orderService.service;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.h5.OrderInfoDto;
import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.vo.h5.TradeVo;

public interface OrderInfoService {
    TradeVo trade(String token);

    Long sumbit(OrderInfoDto orderInfoDto,String token);

    OrderInfo pay(Long orderId);

    TradeVo buy(Long skuId);

    PageInfo<OrderInfo> findByPage(Integer page, Integer limit, Integer orderStatus, String token);

    OrderInfo findByOrderNo(String orderNo);
}
