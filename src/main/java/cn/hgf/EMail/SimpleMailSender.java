package cn.hgf.EMail;

import cn.hgf.poi.FileUtil;
import org.apache.commons.io.FileExistsException;
import org.apache.commons.lang3.StringUtils;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage.RecipientType;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleMailSender {

	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();
	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	/**
	 * 邮箱session
	 */
	private transient Session session;

	public final static   int DEFAULT_SENDER = 0;

	public final static int HK_SENDER = 3;

	public final static int QQ_SENDER = 2;

	public final static int GMAIL_SENDER = 1;
	public final static int WISREADY_SENDER = 4;

	public static SimpleMailSender getSender(int i){
		SimpleMailSender sender = null;
		switch (i){
			case 1 : sender = new SimpleMailSender(
					"smtp.gmail.com",
					"windforceisready@gmail.com",
					"ibxhxyqgokmshduy",
					"587",
					false
			);break;
			case 2 : sender = new SimpleMailSender(
					"smtp.qq.com",
					"1694289437@qq.com",
					"xuhexzqtcpqjfcji",
					"465",
					true
			);break;
			case 3 : sender = new SimpleMailSender(
					"smtp.gomrwind.com",
					"MrWind@gomrwind.com",
					"windforceisready",
					"465",
					true
			);break;
			case 4 : sender = new SimpleMailSender(
					"smtp.exmail.qq.com",
					"gaofan@windforceisready.com",
					"Com.123feng",
					"465",
					true
			);break;
			default : sender = new SimpleMailSender(
					"smtp.163.com",
					"hgf1641197217@163.com",
					"hgf19950626hgf",
					"465",
					true
			);break;
		}
		return sender;
	}



	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 *            SMTP邮件服务器地址
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String smtpHostName, final String username, final String password, final String port,final boolean isSsl) {
		init(username, password, smtpHostName,port,isSsl);
	}

	/**
	 * 初始化邮件发送器
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)，并以此解析SMTP服务器地址
	 * @param password
	 *            发送邮件的密码
	 */
	public SimpleMailSender(final String username, final String password) {
		// 通过邮箱地址解析出smtp服务器，对大多数邮箱都管用
		final String smtpHostName = "smtp." + username.split("@")[1];
		init(username, password, smtpHostName,null,false);

	}

	/**
	 * 初始化
	 * 
	 * @param username
	 *            发送邮件的用户名(地址)
	 * @param password
	 *            密码
	 * @param smtpHostName
	 *            SMTP主机地址
	 */
	private void init(String username, String password, String smtpHostName, String port,boolean isSsl) {
		// 初始化props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		if (port != null) {
			props.put("mail.smtp.port", port);
		}
		props.put("mail.smtp.ssl.enable",isSsl);
//		props.put("mail.transport.protocol", "smtp");
		if (!smtpHostName.contains("163")){
			props.put("mail.smtp.starttls.enable", "true");
		}
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);

		session.setDebug(true);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param subject
	 *            邮件主题
	 * @param content
	 *            邮件内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, String subject, Object content) throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param subject
	 *            主题
	 * @param content
	 *            内容
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 带附件的邮件发送
	 * @param recipients
	 * @param subject
	 * @param content
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, String subject, String content, List<String> filePathList) throws MessagingException, UnsupportedEncodingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		Multipart multipart = new MimeMultipart();
		if (content != null) {
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html; charset=utf-8");
			multipart.addBodyPart(contentPart);
		}

		File file = new File(filePathList.get(0));
		long totalFileSize = file.length();
		//如果有多个文件或者文件大小超标
		if (filePathList.size() > 1 || !FileUtil.verifyFileSize(totalFileSize,getMaxFileSize())){

			String s = FileUtil.zipFiles(null, null, filePathList.toArray(new String[]{}));
			file = new File(s);
		}

		// 设置附件
		MimeBodyPart fileBody = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		fileBody.setDataHandler(new DataHandler(source));
		fileBody.setFileName(file.getPath().substring(file.getPath().lastIndexOf(File.separator) + 1));
		multipart.addBodyPart(fileBody);

		message.setContent(multipart);

		// 发送
		Transport.send(message);
	}

	public void sendWithout(List<String> recipients, String subject, String content, List<String> filePathList) throws MessagingException, UnsupportedEncodingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置主题
		message.setSubject(subject);
		// 设置邮件内容
		Multipart multipart = new MimeMultipart();
		if (content != null) {
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html; charset=utf-8");
			multipart.addBodyPart(contentPart);
		}

		for (String s : filePathList) {
			File file = new File(s);
			// 设置附件
			MimeBodyPart fileBody = new MimeBodyPart();
			DataSource source = new FileDataSource(file);
			fileBody.setDataHandler(new DataHandler(source));
			String fileName = file.getName();
			fileBody.setFileName(fileName);
			multipart.addBodyPart(fileBody);
		}

		message.setContent(multipart);

		// 发送
		Transport.send(message);
	}

	//根据不同的smtp服务器，返回不同的最大附件大小
	public int getMaxFileSize(){
		String hostName = props.get("mail.smtp.host").toString();

		int size = 8;

		switch (hostName){
			case "smtp.gomrwind.com" : size = 8;break;
			case "smtp.qq.com" : size = 15;break;
		}

		return size;
	}

	public boolean verifyAddress(String recipient,String doMain){
		boolean result = false;
		if (StringUtils.isBlank(recipient) || !verifyMail(recipient)){return result;}




		return false;

	}

	/**
	 * 正则表达式校验邮箱
	 * @param mail 待匹配的邮箱
	 * @return 匹配成功返回true 否则返回false;
	 */
	public static boolean verifyMail(String mail){

		String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
		//正则表达式的模式
		Pattern p = Pattern.compile(RULE_EMAIL);
		//正则表达式的匹配器
		Matcher m = p.matcher(mail);
		//进行正则匹配
		return m.matches();
	}


	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 *            收件人邮箱地址
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(String recipient, SimpleMail mail) throws AddressException, MessagingException {
		send(recipient, mail.getSubject(), mail.getContent());
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 *            收件人们
	 * @param mail
	 *            邮件对象
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void send(List<String> recipients, SimpleMail mail) throws AddressException, MessagingException {
		send(recipients, mail.getSubject(), mail.getContent());
	}

	public static boolean valid(String toMail,String doMain){





	    return false;
    }

    public final static List<String> HZ_RECEIVER = Arrays.asList("ceo@123feng.com",
																"comfy.song@gomrwind.com",
																"rogers.yang@gomrwind.com",
																"songxueting@windforceisready.com",
																"huyongtao@windforceisready.com",
																"hans.he@gomrwind.com");

    public final static List<String> RYD_RECEIVER = Arrays.asList("abbas@windforceisready.com",
																"zhangyi@windforceisready.com",
																"pubingxin@windforceisready.com",
																"sami@windforceisready.com",
																"hans.he@gomrwind.com");

    public final static List<String> COD_RYD_RECEIVER = Arrays.asList("pubingxin@windforceisready.com",
																		"mohamed@windforceisready.com",
																		"hans.he@gomrwind.com");



    public final static List<String> OWN_RECEIVER = Arrays.asList("hgf1641197217@163.com","hgftest@126.com","hans.he@gomrwind.com","1641197217@qq.com");


	public static void main(String[] args){


	}
}
