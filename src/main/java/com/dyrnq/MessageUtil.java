package com.dyrnq;

import cn.hutool.core.io.resource.ClassPathResource;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Init;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 国际化工具类
 */
@Component
public class MessageUtil {
	static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	private Properties getPropertis(String name) {
		Properties properties = new Properties();
		try {
			// 使用ClassLoader加载properties配置文件生成对应的输入流
			ClassPathResource resource = new ClassPathResource(name);
			InputStream in = resource.getStream();

			// 使用properties对象加载输入流
			properties.load(in);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}


		return properties;
	}
	
	Properties properties = null;
	Properties propertiesEN = null;
	
	@Init
	private void ini() {
		propertiesEN = getPropertis("i18n/messages_en_US.properties");
		properties = getPropertis("i18n/messages.properties");
	}


	
	/**
	 * 获取单个国际化翻译值
	 */
	public String get(String msgKey,String lang) {
		if (lang != null && lang.equals("en_US")) {
			return propertiesEN.getProperty(msgKey);
		} else {
			return properties.getProperty(msgKey);
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public Properties getPropertiesEN() {
		return propertiesEN;
	}

	public void setPropertiesEN(Properties propertiesEN) {
		this.propertiesEN = propertiesEN;
	}
	
	
}