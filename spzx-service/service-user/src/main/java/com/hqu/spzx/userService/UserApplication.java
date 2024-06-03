package com.hqu.spzx.userService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.hqu.spzx")
public class UserApplication {
    public static void main(String[] args) {

        SpringApplication.run(UserApplication.class,args);
    }
}
