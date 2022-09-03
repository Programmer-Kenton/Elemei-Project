package com.kenton.elemei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author: Kenton
 * @description 项目启动类
 * @date: 2022/9/3 9:45
 */

@Slf4j
@SpringBootApplication
// 添加扫描器
@ServletComponentScan
public class ElemeiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElemeiApplication.class,args);
        log.info("项目启动成功...");
    }
}