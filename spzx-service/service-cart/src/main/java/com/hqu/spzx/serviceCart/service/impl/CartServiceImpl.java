package com.hqu.spzx.serviceCart.service.impl;

import com.alibaba.fastjson2.JSON;
import com.hqu.spzx.feign.product.ProductClient;
import com.hqu.spzx.model.entity.h5.CartInfo;
import com.hqu.spzx.model.entity.product.ProductSku;
import com.hqu.spzx.model.entity.user.UserInfo;
import com.hqu.spzx.serviceCart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private ProductClient productClient;
    @Override
    public void addCart(Long skuId, Integer skuNum,String token) {
        ProductSku productSku = productClient.getDetails(skuId);
        System.out.println(productSku);
        UserInfo userInfo = com.alibaba.fastjson.JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        Object cartInfo = redisTemplate.opsForHash().get("cart:"+userInfo.getId(),String.valueOf(skuId));
        CartInfo cartInfoAdd=new CartInfo();
        if(null==cartInfo){
            cartInfoAdd.setSkuNum(skuNum);
            cartInfoAdd.setCartPrice(productSku.getSalePrice());
            cartInfoAdd.setImgUrl(productSku.getThumbImg());
            cartInfoAdd.setSkuId(productSku.getId());
            cartInfoAdd.setIsChecked(1);
            cartInfoAdd.setUserId(userInfo.getId());
            cartInfoAdd.setSkuName(productSku.getSkuName());
            cartInfoAdd.setUpdateTime(new Date());
            cartInfoAdd.setCreateTime(new Date());
            redisTemplate.opsForHash().put("cart:"+userInfo.getId(),skuId.toString(),JSON.toJSONString(cartInfoAdd));
        }
        else {
            CartInfo cartInfo1 = JSON.parseObject(cartInfo.toString(), CartInfo.class);
            cartInfo1.setSkuNum(cartInfo1.getSkuNum()+skuNum);
            cartInfo1.setIsChecked(1);
            cartInfo1.setUpdateTime(new Date());
            redisTemplate.opsForHash().put("cart:"+userInfo.getId(),String.valueOf(skuId),JSON.toJSONString(cartInfo1));
        }
    }

    @Override
    public List<CartInfo> getCartList(String token) {
        UserInfo userInfo = com.alibaba.fastjson.JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        List<Object> values = redisTemplate.opsForHash().values("cart:"+userInfo.getId());
        if(!CollectionUtils.isEmpty(values)){
            List<CartInfo> infoList=values.stream().map(Json->
                JSON.parseObject(Json.toString(),CartInfo.class)
            ).sorted((o1, o2) -> o1.getCreateTime().compareTo(o2.getCreateTime())).collect(Collectors.toList());
            return infoList;
        }
        return new ArrayList<>();
    }

    @Override
    public void delete(Long skuId,String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        redisTemplate.opsForHash().delete("cart:"+userInfo.getId(),skuId.toString());
    }

    @Override
    public void isChecked(Long skuId, Integer isChecked, String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        Boolean aBoolean = redisTemplate.opsForHash().hasKey("cart:" + userInfo.getId(), skuId.toString());
        if(aBoolean){
            Object o = redisTemplate.opsForHash().get("cart:" + userInfo.getId(), skuId.toString());
            CartInfo cartInfo = JSON.parseObject(o.toString(), CartInfo.class);
            cartInfo.setIsChecked(isChecked);
            redisTemplate.opsForHash().put("cart:"+userInfo.getId(),skuId.toString(),JSON.toJSONString(cartInfo));
        }
    }

    @Override
    public void allIsChecked(String token, Integer isChecked) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        List<Object> values = redisTemplate.opsForHash().values("cart:" + userInfo.getId());
        if(!CollectionUtils.isEmpty(values)){
            values.stream().map(json-> {
                CartInfo cartInfo = JSON.parseObject(json.toString(), CartInfo.class);
                cartInfo.setIsChecked(isChecked);
                return cartInfo;
            }).forEach(item->{
                redisTemplate.opsForHash().put("cart:" + userInfo.getId(),item.getSkuId().toString(),JSON.toJSONString(item));
            });
        }
    }

    @Override
    public void clearCart(String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        redisTemplate.delete("cart:"+userInfo.getId());
    }

    @Override
    public List<CartInfo> getAllChecked(String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        List<Object> values = redisTemplate.opsForHash().values("cart:" + userInfo.getId());
        if(!CollectionUtils.isEmpty(values)){
            List<CartInfo> cartInfos=values.stream()
                    .map(json->JSON.parseObject(json.toString(),CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked()==1)
                    .collect(Collectors.toList());
            return  cartInfos;
        }
        return new ArrayList<>();
    }

    @Override
    public void deleteCart(String token) {
        UserInfo userInfo = JSON.parseObject(redisTemplate.opsForValue().get("token:" + token), UserInfo.class);
        List<Object> values = redisTemplate.opsForHash().values("cart:" + userInfo.getId());
        if(!CollectionUtils.isEmpty(values)){
            values.stream()
                    .map(json->JSON.parseObject(json.toString(),CartInfo.class))
                    .filter(cartInfo -> cartInfo.getIsChecked()==1)
                    .forEach(cart->
                            redisTemplate.opsForHash().delete("cart:"+userInfo.getId(),cart.getSkuId().toString()));
        }
    }
}
