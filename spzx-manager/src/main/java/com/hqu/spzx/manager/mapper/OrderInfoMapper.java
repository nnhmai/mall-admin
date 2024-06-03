package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.dto.order.OrderStatisticsDto;
import com.hqu.spzx.model.entity.order.OrderStatistics;
import com.hqu.spzx.model.vo.order.OrderStatisticsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    OrderStatistics selectOrderStatistics(String createTime);

    List<OrderStatistics> getOrderStatisticsData(OrderStatisticsDto orderStatisticsDto);
}
