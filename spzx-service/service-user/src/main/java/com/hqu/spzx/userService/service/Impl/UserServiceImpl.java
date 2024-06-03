package com.hqu.spzx.userService.service.Impl;

import cn.hutool.core.util.RandomUtil;
import com.hqu.spzx.commonUtils.HttpUtils;
import com.hqu.spzx.commonservice.exception.MyException;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import com.hqu.spzx.userService.service.UserService;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void sendPoneCode(String phone) {
        String code = redisTemplate.opsForValue().get("phone:code:"+phone);
        if(!StringUtils.isEmpty(code)){
            return;
        }
        String random = RandomStringUtils.randomNumeric(4);
        redisTemplate.opsForValue().set("phone:code:"+phone,random,3, TimeUnit.MINUTES);
        sendSms(phone,random);
    }
    // 发送短信方法
    @SneakyThrows
    public void sendSms(String phone, String validateCode) {

        String host = "https://dfsns.market.alicloudapi.com";
        String path = "/data/send_sms";
        String method = "POST";
        String appcode = "b4cf064938124f129f17cdcd477e2836";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+validateCode);
        bodys.put("template_id", "CST_ptdie100");
        bodys.put("phone_number", phone);

        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ResultCodeEnum.SYSTEM_ERROR);
        }
    }
}
