package com.kenton.elemei.service.impl;

import com.kenton.elemei.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author: Kenton
 * @description 实现邮件短信验证服务
 * @date: 2022/9/5 13:55
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    // 邮件发送的邮箱 即发送者
   /* @Value("${spring.mail.username}")
    private String from;*/

    @Resource
    private JavaMailSender mailSender;


    /**
     * 发送文本邮件
     * @param from 发送方
     * @param to 接收方
     * @param subject 邮件主题
     * @param text 邮件文本内容
     */
    @Override
    public void sendSimpleTextMail(String from, String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            // 发件人
            message.setFrom(from);
            // 收件人
            message.setTo(to);
            // 邮件主题
            message.setSubject(subject);
            // 邮件内容
            message.setText(text);
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
        }
    }
}
