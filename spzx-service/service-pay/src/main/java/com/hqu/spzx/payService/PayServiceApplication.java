package com.hqu.spzx.payService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.hqu.spzx.feign.order")
public class PayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayServiceApplication.class,args);
    }
}
