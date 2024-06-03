package com.hqu.spzx.manager.service.impl;

import com.hqu.spzx.manager.mapper.OrderInfoMapper;
import com.hqu.spzx.manager.service.OrderInfoService;
import com.hqu.spzx.model.dto.order.OrderStatisticsDto;
import com.hqu.spzx.model.entity.order.OrderStatistics;
import com.hqu.spzx.model.vo.order.OrderStatisticsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Override
    public OrderStatisticsVo getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto) {
        List<OrderStatistics> orderStatisticsData = orderInfoMapper.getOrderStatisticsData(orderStatisticsDto);
        OrderStatisticsVo orderStatisticsVo=new OrderStatisticsVo();
        orderStatisticsVo.setAmountList(new ArrayList<>());
        orderStatisticsVo.setDateList(new ArrayList<>());
        for (OrderStatistics orderStatisticsDatum : orderStatisticsData) {
            orderStatisticsVo.getAmountList().add(orderStatisticsDatum.getTotalAmount());
            orderStatisticsVo.getDateList().add(orderStatisticsDatum.getCreateTime().toString());
        }
        return orderStatisticsVo;
    }
}
