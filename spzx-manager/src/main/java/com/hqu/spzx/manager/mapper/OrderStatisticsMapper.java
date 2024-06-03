package com.hqu.spzx.manager.mapper;

import com.hqu.spzx.model.entity.order.OrderStatistics;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderStatisticsMapper {
    void insert(OrderStatistics orderStatistics);
}
