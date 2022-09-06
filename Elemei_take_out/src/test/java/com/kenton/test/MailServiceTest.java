package com.kenton.test;

import com.kenton.elemei.ElemeiApplication;
import com.kenton.elemei.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: Kenton
 * @description 验证码邮件发送测试
 * @date: 2022/9/5 14:07
 */
@Slf4j
@SpringBootTest(classes = ElemeiApplication.class)
@RunWith(SpringRunner.class)
public class MailServiceTest {


    @Resource
    private MailService mailService;

    @Value("${spring.mail.username}")
    private String from;

    @Test
    public void sendSimpleTextMailTest(){
        System.out.println("-----------");
        String to = "1441630907@qq.com";
        String subject = "SpringBoot发送的文本邮件";
        String text = "<p>第一封 SpringBoot文本邮件</p>";
        mailService.sendSimpleTextMail(from,to,subject,text);
        log.info("文本邮件发送成功 to = {}",to);
    }
}
