package com.hqu.spzx.payService.service;

import com.hqu.spzx.model.entity.pay.PaymentInfo;

public interface PaymentService {
    PaymentInfo save(String OrderNo);
}
