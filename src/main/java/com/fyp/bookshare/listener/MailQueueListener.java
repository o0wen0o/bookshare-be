package com.fyp.bookshare.listener;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 用于处理邮件发送的消息队列监听器，监听RabbitMQ是否接收信息
 * Message queue listener used to handle email sending, monitoring whether RabbitMQ receives information
 */
@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {

    @Resource
    JavaMailSender sender;

    @Value("${spring.mail.username}")
    String username;

    /**
     * Handle email sending
     *
     * @param data email info
     */
    @RabbitHandler
    public void sendMailMessage(Map<String, Object> data) {
        String email = data.get("email").toString();
        Integer code = (Integer) data.get("code");
        SimpleMailMessage message = switch (data.get("type").toString()) {
            case "register" -> createMessage("Welcome to register our website",
                    "Your email registration verification code is:" + code +
                            ", which is valid for 3 minutes. To protect the security of your account, please do not disclose the verification code information to others.",
                    email);
            case "reset" -> createMessage("Reset password email",
                    "You are performing a password reset operation. The verification code is: " + code +
                            ", which is valid for 3 minutes. If it is not done by yourself, please ignore it.",
                    email);
            default -> null;
        };
        if (message == null) return;
        sender.send(message);
    }

    /**
     * Encapsulate simple email message entities
     *
     * @param title
     * @param content
     * @param email
     * @return Mail entity
     */
    private SimpleMailMessage createMessage(String title, String content, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(title);
        message.setText(content);
        message.setTo(email);
        message.setFrom(username);
        return message;
    }
}
