package com.hqu.spzx.manager;

import com.hqu.spzx.manager.properties.UserAuthProperties;
import com.hqu.spzx.properties.MinioProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan("com.hqu.spzx")
@EnableConfigurationProperties({UserAuthProperties.class, MinioProperties.class})
@EnableScheduling
@EnableAsync
@EnableAspectJAutoProxy
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }

}
