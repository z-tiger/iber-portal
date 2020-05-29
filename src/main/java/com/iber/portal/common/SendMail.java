package com.iber.portal.common;
import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;

import com.iber.portal.service.sys.SysParamService;

/**
 * 发送邮件的测试程序
 * 
 */
public class SendMail {

    public static void main(String[] args) throws Exception {
        String subject = "粤B12345车辆违章";
    	StringBuffer sendMailContent = new StringBuffer();
		sendMailContent.append("<b>车牌号码：</b>").append("粤B12345").append("<br/>");
		sendMailContent.append("<b>违章时间：</b>").append("2013-12-29 11:57:29").append("<br/>");
		sendMailContent.append("<b>违章地点：</b>").append("316省道53KM+200M违章").append("<br/>");
		sendMailContent.append("<b>违章行为：</b>").append("驾驶中型以上载客载货汽车、校车、危险物品运输车辆以外的其他机动车在高速公路以外的道路上行驶超过").append("<br/>");
		sendMailContent.append("<b>违章扣分：</b>").append("6").append("<br/>");
		sendMailContent.append("<b>违章扣款(单位：元)：</b>").append("200").append("<br/>");
        String to = "@xcloudtrip.com";
//        SendMail sendMail = new SendMail();
//        sendMail.sendMail(subject, sendMailContent.toString(), to, "", "");
    }
    
    private SysParamService sysParamService ;
    
    public SendMail(SysParamService sysParamService) {
		super();
		this.sysParamService = sysParamService;
	}

	public void sendMail(String subject,String content,String _to) throws Exception{
    	sendMail(subject, content, _to, "", "");
    }
    
    public void sendMail(String subject,String content,String _to,String _cc) throws Exception{
    	sendMail(subject, content, _to, _cc, "");
    }
    
    /**
     * 发送邮件
     * @param subject 主题
     * @param content 邮件内容
     * @param _to 收件人
     * @param _cc 抄送人
     * @param _bcc 密送入
     * @throws Exception
     */
    public void sendMail(String subject,String content,String _to,String _cc,String _bcc) throws Exception{
    	// 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        String auth = sysParamService.selectByKey("mail.smtp.auth").getValue();
        String host = sysParamService.selectByKey("mail.smtp.host").getValue();
        String user = sysParamService.selectByKey("mail.user").getValue();
        String password = sysParamService.selectByKey("mail.password").getValue();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.host", host);
        // 发件人的账号
        props.put("mail.user", user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人
        InternetAddress to = new InternetAddress(_to);
        message.setRecipient(RecipientType.TO, to);

        // 设置抄送
        if(!"".equals(_cc) && null!=_cc){
        	InternetAddress cc = new InternetAddress(_cc);
        	message.setRecipient(RecipientType.CC, cc);
        }

        // 设置密送，其他的收件人不能看到密送的邮件地址
        if(!"".equals(_bcc) && null!=_bcc){
        	InternetAddress bcc = new InternetAddress(_bcc);
        	message.setRecipient(RecipientType.BCC, bcc);
        }

        // 设置邮件标题
        message.setSubject(subject);

        // 设置邮件的内容体
        message.setContent(content, "text/html;charset=UTF-8");

        // 发送邮件
        Transport.send(message);
    }

    /**
     * 发送邮件
     * @param subject 主题
     * @param content 邮件内容
     * @param _to 收件人
     * @param _cc 抄送人
     * @param _bcc 密送入
     * @throws Exception
     */
    public void sendMail(String subject,String content,String _to,String _cc,String _bcc,List<File> files) throws Exception{
        // 配置发送邮件的环境属性
        final Properties props = new Properties();
        // 表示SMTP发送邮件，需要进行身份验证
        String auth = sysParamService.selectByKey("mail.smtp.auth").getValue();
        String host = sysParamService.selectByKey("mail.smtp.host").getValue();
        String user = sysParamService.selectByKey("mail.user").getValue();
        String password = sysParamService.selectByKey("mail.password").getValue();
        // 表示SMTP发送邮件，需要进行身份验证
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.host", host);
        // 发件人的账号
        props.put("mail.user", user);
        // 访问SMTP服务时需要提供的密码
        props.put("mail.password", password);

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                // 用户名、密码
                String userName = props.getProperty("mail.user");
                String password = props.getProperty("mail.password");
                return new PasswordAuthentication(userName, password);
            }
        };
        // 使用环境属性和授权信息，创建邮件会话
        Session mailSession = Session.getInstance(props, authenticator);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(mailSession);
        // 设置发件人
        InternetAddress form = new InternetAddress(props.getProperty("mail.user"));
        message.setFrom(form);

        // 设置收件人

        InternetAddress[] internetAddressTo = InternetAddress.parse(_to);
        message.setRecipients(Message.RecipientType.TO, internetAddressTo);

        // 设置抄送
        if(!"".equals(_cc) && null!=_cc){
            InternetAddress[] cc = InternetAddress.parse(_cc);
            message.setRecipients(RecipientType.CC, cc);
        }

        // 设置密送，其他的收件人不能看到密送的邮件地址
        if(!"".equals(_bcc) && null!=_bcc){
            InternetAddress bcc = new InternetAddress(_bcc);
            message.setRecipient(RecipientType.BCC, bcc);
        }

        Multipart mp = new MimeMultipart();
        MimeBodyPart mbpContent = new MimeBodyPart();
        mbpContent.setText(content);
        mp.addBodyPart(mbpContent);
         /* 往邮件中添加附件 */
        if (files != null) {
            for (File file:files) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(file);
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(new String(file.getName().getBytes("utf-8"),"ISO-8859-1"));
                mp.addBodyPart(mbpFile);
            }
            System.out.println("添加成功");
        }

        message.setContent(mp);
        // 设置邮件标题
        message.setSubject(subject);

        // 发送邮件
        Transport.send(message);
    }
}
