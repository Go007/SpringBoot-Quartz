package com.hong.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author wanghong
 * @date 2020/03/06 22:04
 **/
@SpringBootApplication
@MapperScan("com.hong.quartz.mapper")
@EnableAsync
public class QuartzApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuartzApplication.class,args);
    }
}
