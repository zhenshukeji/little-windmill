package com.zhenshu.parent.common.library.email;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author jing
 * @version 1.0
 * @desc 邮件发送
 * @date 2021/8/24 0024 10:09
 **/
@Service
public class EmailService {

    @Resource
    private EmailConfig emailConfig;

    /**
     * 给一个人发邮件
     */
    public void sendEmail(String email, String content, String title) throws Exception {
        sendEmail(new String[]{email}, content, title);
    }

    /**
     * 给多个人发邮件
     * @param emailArr 邮件列表
     * @param content 内容
     * @param title 标题
     * @throws Exception 异常
     */
    public void sendEmail(String[] emailArr, String content, String title) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        // 参数配置
        Properties props = new Properties();
        /*
         * SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
         * 需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
         * QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
         */
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", "smtp");
        // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.host", emailConfig.getMyEmailHost());
        // 需要请求认证
        props.setProperty("mail.smtp.auth", "true");

        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 设置为debug模式, 可以查看详细的发送 log
        session.setDebug(true);

        // 3. 创建一封邮件
        MimeMessage message = createMimeMessage(session, emailConfig.getAccount(), emailArr, content, title);
        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //
        //    PS_01: 如果连接服务器失败, 都会在控制台输出相应失败原因的log。
        //    仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接,
        //    根据给出的错误类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        //    PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //           (1) 邮箱没有开启 SMTP 服务;
        //           (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //           (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //           (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //           (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        transport.connect(emailConfig.getAccount(), emailConfig.getPassword());

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    /**
     * 创建一封只包含文本的简单邮件
     *
     * @param session        和服务器交互的会话
     * @param sendMail       发件人邮箱
     * @param receiveMailArr 收件人邮箱
     * @return 邮件配置
     */
    public MimeMessage createMimeMessage(Session session, String sendMail, String[] receiveMailArr, String content, String title) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人
        message.setFrom(new InternetAddress(sendMail, emailConfig.getSender(), "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        for (String receiveMail : receiveMailArr) {
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "UTF-8"));
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "UTF-8"));
        }

        // 4. Subject: 邮件主题
        message.setSubject(title, "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

}
