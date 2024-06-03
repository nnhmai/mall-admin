package com.hqu.spzx.gateway.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hqu.spzx.model.entity.user.UserInfo;
import com.hqu.spzx.model.vo.common.Result;
import com.hqu.spzx.model.vo.common.ResultCodeEnum;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Order {
    @Autowired
    private RedisTemplate<String , String> redisTemplate;

    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();
        if(antPathMatcher.match("/api/**/auth/**",path)){
            UserInfo userInfo = get(request);
            if(null==userInfo){
                out(exchange.getResponse(), ResultCodeEnum.LOGIN_AUTH);
            }
        }
        return chain.filter(exchange);
    }
    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum resultCodeEnum) {
        Result result = Result.build(null, resultCodeEnum);
        byte[] bits = JSONObject.toJSONString(result).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        //指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    private UserInfo get(ServerHttpRequest request){
        List<String> tokenList = request.getHeaders().get("token");
        if(!CollectionUtils.isEmpty(tokenList)){
            String token = tokenList.get(0);
            String s = redisTemplate.opsForValue().get("token:"+token);
            if(!StringUtils.isEmpty(s)){
                UserInfo userInfo = JSON.parseObject(s, UserInfo.class);
                return userInfo;
            }
        }
        return null;
    }

    @Override
    public int value() {
        return 0;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return null;
    }
}
