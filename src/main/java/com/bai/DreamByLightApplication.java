package com.bai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.baomidou.mybatisplus.samples.quickstart.mapper")
@MapperScan("com.bai.mapper")
public class DreamByLightApplication {

    public static void main(String[] args) {
        SpringApplication.run(DreamByLightApplication.class, args);
    }

}
