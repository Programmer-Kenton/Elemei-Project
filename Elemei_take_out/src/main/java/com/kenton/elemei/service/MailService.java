package com.kenton.elemei.service;

/**
 * 邮件服务接口
 */
public interface MailService {
    void sendSimpleTextMail(String from,String to, String subject, String text);
}
