package com.hqu.spzx.payService.service.impl;

import com.hqu.spzx.feign.order.OrderClient;
import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.entity.order.OrderItem;
import com.hqu.spzx.model.entity.pay.PaymentInfo;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.payService.mapper.PaymentMapper;
import com.hqu.spzx.payService.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private OrderClient orderClient;
    @Autowired
    private PaymentMapper paymentMapper;
    @Override
    public PaymentInfo save(String orderNo) {
        PaymentInfo paymentInfo = paymentMapper.findByOrderNo(orderNo);
        if(null==paymentInfo){
            OrderInfo orderInfo = orderClient.findByOrderNo(orderNo).getData();
            paymentInfo.setAmount(orderInfo.getTotalAmount());
            paymentInfo.setUserId(orderInfo.getUserId());
            paymentInfo.setPayType(orderInfo.getPayType());
            String context="";
            for (OrderItem orderItem : orderInfo.getOrderItemList()) {
                context+=orderItem.getSkuName()+" ";
            }
            paymentInfo.setContent(context);
            paymentMapper.save(paymentInfo);
        }
        return paymentInfo;
    }
}
