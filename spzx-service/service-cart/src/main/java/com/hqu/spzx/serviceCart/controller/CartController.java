package com.hqu.spzx.serviceCart.controller;

import com.hqu.spzx.model.entity.h5.CartInfo;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.serviceCart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @GetMapping("auth/addToCart/{skuId}/{skuNum}")
    public Result addCart(@PathVariable Long skuId,
                          @PathVariable Integer skuNum,
                          @RequestHeader("token") String token){
        cartService.addCart(skuId,skuNum,token);
        return Result.build(null, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("auth/cartList")
    public Result<List<CartInfo>> cartList(@RequestHeader("token") String token) {
        List<CartInfo> cartInfoList = cartService.getCartList(token);
        return Result.build(cartInfoList, ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/deleteCart/{skuId}")
    public Result deleteCart(@PathVariable Long skuId,
                             @RequestHeader("token") String token){
        cartService.delete(skuId,token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/checkCart/{skuId}/{isChecked}")
    public Result isCheckCart(@PathVariable Long skuId,
                              @PathVariable Integer isChecked,
                              @RequestHeader String token){
        cartService.isChecked(skuId,isChecked,token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/allCheckCart/{isChecked}")
    public Result allCheckCart(@RequestHeader String token,
                               @PathVariable Integer isChecked){
        cartService.allIsChecked(token,isChecked);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @GetMapping("/auth/clearCart")
    public Result clearCart(@RequestHeader String token){
        cartService.clearCart(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @GetMapping(value = "/auth/getAllChecked")
    public List<CartInfo> getAllChecked(@RequestHeader String token) {
        List<CartInfo> cartInfoList = cartService.getAllChecked(token) ;
        return cartInfoList;
    }
    @GetMapping("/auth/deleteChecked")
    public Result deleteCart(@RequestHeader String token){
        cartService.deleteCart(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
