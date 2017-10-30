package com.yonyou.utils.infrastructure;

import com.graduation.core.base.exception.BusinessException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 发送电子邮件
 */
public class SendMailUtil {

	private static final String from = "thinkgem@163.com";
	private static final String fromName = "测试公司";
	private static final String charSet = "utf-8";
	private static final String username = "thinkgem@163.com";
	private static final String password = "123456";

	private static Map<String, String> hostMap = new HashMap<>();
	static {
		// 126
		hostMap.put("smtp.126", "smtp.126.com");
		// qq
		hostMap.put("smtp.qq", "smtp.qq.com");
		// 163
		hostMap.put("smtp.163", "smtp.163.com");
		// sina
		hostMap.put("smtp.sina", "smtp.sina.com.cn");
		// tom
		hostMap.put("smtp.tom", "smtp.tom.com");
		// 263
		hostMap.put("smtp.263", "smtp.263.net");
		// yahoo
		hostMap.put("smtp.yahoo", "smtp.mail.yahoo.com");
		// hotmail
		hostMap.put("smtp.hotmail", "smtp.live.com");
		// gmail
		hostMap.put("smtp.gmail", "smtp.gmail.com");
		hostMap.put("smtp.port.gmail", "465");
	}

	public static String getHost(String email) throws Exception {
		Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
		Matcher matcher = pattern.matcher(email);
		String key = "unSupportEmail";
		if (matcher.find()) {
			key = "smtp." + matcher.group(1);
		}
		if (hostMap.containsKey(key)) {
			return hostMap.get(key);
		} else {
			throw new Exception("unSupportEmail");
		}
	}

	public static int getSmtpPort(String email) throws Exception {
		Pattern pattern = Pattern.compile("\\w+@(\\w+)(\\.\\w+){1,2}");
		Matcher matcher = pattern.matcher(email);
		String key = "unSupportEmail";
		if (matcher.find()) {
			key = "smtp.port." + matcher.group(1);
		}
		if (hostMap.containsKey(key)) {
			return Integer.parseInt(hostMap.get(key));
		} else {
			return 25;
		}
	}

	/**
	 * 发送模板邮件
	 * @param toMailAddr 收信人地址
	 * @param subject email主题
	 * @param templatePath 模板地址
	 * @param map 模板map
	 */
	public static void sendFtlMail(String toMailAddr, String subject,
			String templatePath, Map<String, Object> map) {
		Template template;
		Configuration freeMarkerConfig;
		HtmlEmail email = new HtmlEmail();
		try {
			email.setHostName(getHost(from));
			email.setSmtpPort(getSmtpPort(from));
			email.setCharset(charSet);
			email.addTo(toMailAddr);
			email.setFrom(from, fromName);
			email.setAuthentication(username, password);
			email.setSubject(subject);
			freeMarkerConfig = new Configuration();
			freeMarkerConfig.setDirectoryForTemplateLoading(new File(getFilePath()));
			// 获取模板
			template = freeMarkerConfig.getTemplate(getFileName(templatePath),
					new Locale("Zh_cn"), "UTF-8");
			// 模板内容转换为string
			String htmlText = FreeMarkerTemplateUtils
					.processTemplateIntoString(template, map);
			email.setMsg(htmlText);
			email.send();
		} catch (Exception e) {
			throw new BusinessException("发送邮件失败！");
		}
	}

	/**
	 * 发送普通邮件
	 * @param toMailAddr 收信人地址
	 * @param subject email主题
	 * @param message 发送email信息
	 */
	public static void sendCommonMail(String toMailAddr, String subject,
			String message) {
		HtmlEmail email = new HtmlEmail();
		try {
			email.setHostName(getHost(from));
			email.setSmtpPort(getSmtpPort(from));
			email.setCharset(charSet);
			email.addTo(toMailAddr);
			email.setFrom(from, fromName);
			email.setAuthentication(username, password);
			email.setSubject(subject);
			email.setMsg(message);
			email.send();
		} catch (Exception e) {
			throw new BusinessException("发送邮件失败！");
		}
	}

	/**
	 * 获取模板内容
	 * @param templatePath 模板路径
	 * @param map	替换内容
	 * @return 模板内容
	 */
	public static String getHtmlText(String templatePath, Map<String, Object> map) {
		Template template;
		String htmlText;
		try {
			Configuration freeMarkerConfig;
			freeMarkerConfig = new Configuration();
			freeMarkerConfig.setDirectoryForTemplateLoading(new File(getFilePath()));
			// 获取模板
			template = freeMarkerConfig.getTemplate(getFileName(templatePath),
					new Locale("Zh_cn"), "UTF-8");
			// 模板内容转换为string
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(
					template, map);
		} catch (Exception e) {
			throw new BusinessException("获取模板内容失败！");
		}
		return htmlText;
	}

	private static String getFilePath() {
		String path = getAppPath(SendMailUtil.class);
		path = path + File.separator + "mailtemplate" + File.separator;
		path = path.replace("\\", "/");
		System.out.println(path);
		return path;
	}

	private static String getFileName(String path) {
		path = path.replace("\\", "/");
		System.out.println(path);
		return path.substring(path.lastIndexOf("/") + 1);
	}

	/**
	 * 获取程序路径
	 * @param cls 类
	 * @return 程序路径
	 */
	public static String getAppPath(Class<?> cls) {
		// 检查用户传入的参数是否为空
		if (cls == null)
			throw new IllegalArgumentException("参数不能为空！");
		ClassLoader loader = cls.getClassLoader();
		// 获得类的全名，包括包名
		String clsName = cls.getName() + ".class";
		// 获得传入参数所在的包
		Package pack = cls.getPackage();
		StringBuilder path = new StringBuilder();
		// 如果不是匿名包，将包名转化为路径
		if (pack != null) {
			String packName = pack.getName();
			// 此处简单判定是否是Java基础类库，防止用户传入JDK内置的类库
			if (packName.startsWith("java.") || packName.startsWith("javax."))
				throw new IllegalArgumentException("不要传送系统类！");
			// 在类的名称中，去掉包名的部分，获得类的文件名
			clsName = clsName.substring(packName.length() + 1);
			// 判定包名是否是简单包名，如果是，则直接将包名转换为路径，
			if (!packName.contains("."))
				path = new StringBuilder(packName + "/");
			else {// 否则按照包名的组成部分，将包名转换为路径
				int start = 0, end;
				end = packName.indexOf(".");
				while (end != -1) {
					path.append(packName.substring(start, end)).append("/");
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path.append(packName.substring(start)).append("/");
			}
		}
		// 调用ClassLoader的getResource方法，传入包含路径信息的类文件名
		java.net.URL url = loader.getResource(path + clsName);
		if(url == null) {
			return null;
		}
		// 从URL对象中获取路径信息
		String realPath = url.getPath();
		// 去掉路径信息中的协议名"file:"
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		// 去掉路径信息最后包含类文件信息的部分，得到类所在的路径
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 1);
		// 如果类文件被打包到JAR等文件中时，去掉对应的JAR等打包文件名
		if (realPath.endsWith("!"))
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		/*------------------------------------------------------------ 
		 ClassLoader的getResource方法使用了utf-8对路径信息进行了编码，当路径 
		  中存在中文和空格时，他会对这些字符进行转换，这样，得到的往往不是我们想要 
		  的真实路径，在此，调用了URLDecoder的decode方法进行解码，以便得到原始的 
		  中文及空格路径 
		-------------------------------------------------------------*/
		try {
			realPath = java.net.URLDecoder.decode(realPath, "utf-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return realPath;
	}
}