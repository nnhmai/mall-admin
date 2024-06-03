package com.hqu.spzx.orderService.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.feign.CartClient;
import com.hqu.spzx.feign.UserClient;
import com.hqu.spzx.feign.product.ProductClient;
import com.hqu.spzx.model.dto.h5.OrderInfoDto;
import com.hqu.spzx.model.entity.h5.CartInfo;
import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.entity.order.OrderItem;
import com.hqu.spzx.model.entity.order.OrderLog;
import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.model.entity.user.UserAddress;
import com.hqu.spzx.model.entity.user.UserInfo;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.h5.TradeVo;
import com.hqu.spzx.orderService.mapper.OrderInfoMapper;
import com.hqu.spzx.orderService.mapper.OrderItemMapper;
import com.hqu.spzx.orderService.mapper.OrderLogMapper;
import com.hqu.spzx.orderService.service.OrderInfoService;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private CartClient cartClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderLogMapper orderLogMapper;
    @Override
    public TradeVo trade(String token) {
        List<CartInfo> cartInfos = cartClient.AllChecked(token);
        System.out.println(cartInfos);
        BigDecimal total=new BigDecimal(0);
        TradeVo tradeVo=new TradeVo();
        List<OrderItem> orderItems=new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartInfos)){
            for (CartInfo cartInfo : cartInfos) {
                total=total.add(cartInfo.getCartPrice().multiply(new BigDecimal(cartInfo.getSkuNum())));
                OrderItem orderItem=new OrderItem();
                orderItem.setSkuId(cartInfo.getSkuId());
                orderItem.setSkuNum(cartInfo.getSkuNum());
                orderItem.setThumbImg(cartInfo.getImgUrl());
                orderItem.setSkuName(cartInfo.getSkuName());
                orderItem.setSkuPrice(cartInfo.getCartPrice());
                orderItems.add(orderItem);
            }
        }
        tradeVo.setOrderItemList(orderItems);
        tradeVo.setTotalAmount(total);
        return tradeVo;
    }
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @SneakyThrows
    @Override
    @Transactional
    public Long sumbit(OrderInfoDto orderInfoDto,String token) {
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if(CollectionUtils.isEmpty(orderItemList)){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productClient.getDetails(orderItem.getSkuId());
            if(productSku.getStockNum().intValue()<=0){
                throw new MyException(ResultCodeEnum.STOCK_LESS);
            }
        }
        UserAddress userAddress = userClient.findById(orderInfoDto.getUserAddressId());
        OrderInfo orderInfo = getOrderInfo(orderInfoDto, userAddress);
        BigDecimal total=new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            total=total.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setOriginalTotalAmount(total);
        orderInfo.setTotalAmount(total.subtract(orderInfoDto.getFeightFee()));
        orderInfoMapper.save(orderInfo);
        orderItemMapper.batchInsert(orderItemList,orderInfo.getId());
        OrderLog orderLog=new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setNote("提交订单");
        orderLog.setProcessStatus(0);
        orderLog.setOperateUser(userAddress.getUserId().toString());
        orderLogMapper.save(orderLog);
        cartClient.deleteChecked(token);
        return orderInfo.getId();
    }

    @Override
    public OrderInfo pay(Long orderId) {
        OrderInfo orderInfo=orderInfoMapper.findById(orderId);
        return orderInfo;
    }

    @Override
    public TradeVo buy(Long skuId) {
        ProductSku productSku = productClient.getDetails(skuId);
        OrderItem orderItem=new OrderItem();
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setSkuId(productSku.getId());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        TradeVo tradeVo=new TradeVo();
        tradeVo.setTotalAmount(orderItem.getSkuPrice());
        List<OrderItem> list=new ArrayList<>();
        list.add(orderItem);
        tradeVo.setOrderItemList(list);
        return tradeVo;
    }

    @Override
    public PageInfo<OrderInfo> findByPage(Integer page, Integer limit, Integer orderStatus, String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        PageHelper.startPage(page,limit);
        List<OrderInfo> orderInfos=orderInfoMapper.findByStatus(userInfo.getId(),orderStatus);
        PageInfo<OrderInfo> list = new PageInfo<>(orderInfos);
        for (OrderInfo orderInfo : list.getList()) {
            List<OrderItem>orderItems=orderItemMapper.findByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItems);
        }
        return list;
    }

    @Override
    public OrderInfo findByOrderNo(String orderNo) {
        OrderInfo orderInfo= orderInfoMapper.findByOrderNo(orderNo);
        List<OrderItem> byOrderId = orderItemMapper.findByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(byOrderId);
        return orderInfo;
    }

    @NotNull
    private OrderInfo getOrderInfo(OrderInfoDto orderInfoDto, UserAddress userAddress) {
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        orderInfo.setUserId(userAddress.getUserId());
        orderInfo.setPayType(2);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setNickName(userAddress.getName());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setOrderStatus(0);
        orderInfo.setRemark(orderInfoDto.getRemark());
        return orderInfo;
    }
}
