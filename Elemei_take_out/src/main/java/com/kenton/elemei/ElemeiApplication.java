package com.kenton.elemei;

import com.kenton.elemei.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author: Kenton
 * @description 项目启动类
 * @date: 2022/9/3 9:45
 */

@Slf4j
@SpringBootApplication
// 添加扫描器
@ServletComponentScan
// 开启事务支持
@EnableTransactionManagement
public class ElemeiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElemeiApplication.class,args);
        log.info("项目启动成功...");


    }

}
