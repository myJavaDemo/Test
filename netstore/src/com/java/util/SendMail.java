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
			message.setSubject("����netstore��վ�ļ����ʼ�");
			
			message.setContent("�װ���"+c.getUsename()+"<br/>��л��ע���Ϊ���ǵĻ�Ա�����ʹ����漤�������˻���<br/><a href='http://localhost:8080/netstore/servlet/ClientServlet?op=active&code="+c.getCode()+"'>�������</a><br/>���ʼ���ϵͳ�Զ��������벻Ҫֱ�ӻظ���", "text/html;charset=UTF-8");
			message.saveChanges();
			
			Transport ts = session.getTransport("smtp");
			ts.connect("lyshine18", "ly1575177");
			ts.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

















