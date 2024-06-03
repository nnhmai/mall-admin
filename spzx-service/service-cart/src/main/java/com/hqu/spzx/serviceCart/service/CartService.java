package com.hqu.spzx.serviceCart.service;

import com.hqu.spzx.model.entity.h5.CartInfo;

import java.util.List;

public interface CartService {
    void addCart(Long skuId, Integer skuNum,String token);

    List<CartInfo> getCartList(String token);

    void delete(Long skuId,String token);

    void isChecked(Long skuId, Integer isChecked, String token);

    void allIsChecked(String token, Integer isChecked);

    void clearCart(String token);

    List<CartInfo> getAllChecked(String token);

    void deleteCart(String token);
}
