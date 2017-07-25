package com.java.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.java.domain.Customer;

public class SendMail extends Thread {
	private Customer c;
	
	public SendMail(Customer c){
		this.c = c;
	}
	
	public void run(){
		try {
			Properties props = new Properties();
			props.setProperty("mail.transport.protocal","smtp");
			props.setProperty("mail.host", "smtp.163.com");
			props.setProperty("mail.smtp.auth","true");
			Session session = Session.getInstance(props);
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress("lyshine18@163.com"));
			message.setRecipients(Message.RecipientType.TO, c.getEmail());
			message.setSubject("来自netstore网站的激活邮件");
			
			message.setContent("亲爱的"+c.getUsename()+"<br/>感谢您注册成为我们的会员，请猛戳下面激活您的账户。<br/><a href='http://localhost:8080/netstore/servlet/ClientServlet?op=active&code="+c.getCode()+"'>点击这里</a><br/>本邮件由系统自动发出，请不要直接回复。", "text/html;charset=UTF-8");
			message.saveChanges();
			
			Transport ts = session.getTransport("smtp");
			ts.connect("lyshine18", "ly1575177");
			ts.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

















