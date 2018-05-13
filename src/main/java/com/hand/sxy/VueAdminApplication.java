package com.hand.sxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author spilledyear
 * <p>
 * 如果不加 MapperScan 这个注解，会提示 xxxMapper 不是一个有效Been
 */
@SpringBootApplication
@MapperScan("com.hand.sxy.*.mapper")
public class VueAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueAdminApplication.class, args);
    }
}
