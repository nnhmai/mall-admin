package com.hqu.spzx.payService.mapper;

import com.hqu.spzx.model.entity.pay.PaymentInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaymentMapper {
    void save(PaymentInfo paymentInfo);
    PaymentInfo findByOrderNo(String OrderNo);
}
