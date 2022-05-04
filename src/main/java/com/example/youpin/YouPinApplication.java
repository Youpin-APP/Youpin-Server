package com.example.youpin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.youpin.Mapper")
public class YouPinApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouPinApplication.class, args);
    }

}
