package com.hqu.spzx.orderService.controller;

import com.github.pagehelper.PageInfo;
import com.hqu.spzx.model.dto.h5.OrderInfoDto;
import com.hqu.spzx.model.entity.order.OrderInfo;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.model.vo.h5.TradeVo;
import com.hqu.spzx.orderService.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order/orderInfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;
    @GetMapping("auth/trade")
    public Result<TradeVo> trade(@RequestHeader String token){
        TradeVo tradeVo=orderInfoService.trade(token);
        return Result.build(tradeVo, ResultCodeEnum.SUCCESS);
    }
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoDto orderInfoDto,
                              @RequestHeader String token){
        Long orderId=orderInfoService.sumbit(orderInfoDto,token);
        return Result.build(orderId,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/{orderId}")
    public Result<OrderInfo> pay(@PathVariable Long orderId){
        OrderInfo orderInfo=orderInfoService.pay(orderId);
        return  Result.build(orderInfo,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/buy/{skuId}")
    public Result<TradeVo> buy(@PathVariable Long skuId){
        TradeVo tradeVo=orderInfoService.buy(skuId);
        return Result.build(tradeVo,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/{page}/{limit}")
    public Result<PageInfo<OrderInfo>> findOrder(@PathVariable Integer page,
                                                 @PathVariable Integer limit,
                                                 @RequestParam(value = "orderStatus",required = false) Integer orderStatus,
                                                 @RequestHeader String token){
        PageInfo<OrderInfo> list=orderInfoService.findByPage(page,limit,orderStatus,token);
        return Result.build(list,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("auth/getOrderInfoByOrderNo/{orderNo}")
    public Result<OrderInfo> findByOrderNo(@PathVariable String orderNo){
        OrderInfo orderInfo=orderInfoService.findByOrderNo(orderNo);
        return Result.build(orderInfo,ResultCodeEnum.SUCCESS);
    }
}
