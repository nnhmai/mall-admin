package com.hqu.spzx.manager.service;

import com.hqu.spzx.model.dto.order.OrderStatisticsDto;
import com.hqu.spzx.model.vo.order.OrderStatisticsVo;

public interface OrderInfoService {
    OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
