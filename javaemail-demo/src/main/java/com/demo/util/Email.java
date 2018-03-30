package com.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class Email {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SimpleMailMessage simpleMailMessage;

    /**
     * @方法名: sendMail
     * @参数名：@param subject  邮件主题
     * @参数名：@param content 邮件主题内容
     * @参数名：@param from        发件人Email地址
     * @参数名：@param to         收件人Email地址
     * @描述语: 发送邮件
     */
    public void sendMail(String subject, String content, String from, String to) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true,"UTF-8");
            messageHelper.setFrom(from); //设置发件人Email
            messageHelper.setSubject(subject); //设置邮件主题
            // messageHelper.setText(content);   //设置邮件主题内容
            messageHelper.setTo(to);          //设定收件人Email

            messageHelper.setText(
                    "<html>" +
                            "<body><h1>" + content +
                            "</h1></body>" +
                            "</html>", true);
            javaMailSender.send(mimeMessage);    //发送HTML邮件

        } catch (Exception e) {
            System.out.println("异常信息：" + e);
        }
    }

}
