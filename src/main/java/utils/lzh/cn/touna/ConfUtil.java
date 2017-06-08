package utils.lzh.cn.touna;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 *                       
 * @Filename ConfUtil.java
 *
 * @Description 配置文件的工具类，主要用于读取配置信息，例如：properties里面的内容
 *
 * @Version 1.0
 *
 * @Author leeo
 */

public class ConfUtil {
	private static ConfUtil				initor		= new ConfUtil();
	
	private static Map<String, Object>	configMap	= new HashMap<String, Object>();
	
	private ConfUtil() {
	}
	
	/**
	 * 获取配置文件中key为property的内容
	 * @param configFile	配置文件
	 * @param property	属性
	 * @return
	 */
	public static String get(String configFile, String property) {
		if (!configMap.containsKey(configFile)) {
			initor.initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) configMap.get(configFile);
		String value = config.getString(property);
		return value;
	}
	/**
	 * 获取配置文件中key为property的内容
	 * @param configFile	配置文件
	 * @param property	属性
	 * @param defaultValue 默认值
	 * @return
	 */
	public static int getInt(String configFile, String property,int defaultValue) {
		if (!configMap.containsKey(configFile)) {
			initor.initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) configMap.get(configFile);
		try{
			String value = config.getString(property);
			if(StringUtils.isNumeric(value)){
				return Integer.parseInt(value);
			}
		}catch(Exception ex){
			return defaultValue;
		}
		return defaultValue;
	}
	
	/**
	 * 返回列表
	 * 例如：olors.pie = #FF0000, #00FF00, #0000FF
	 * 	或者
	 * # chart colors
		colors.pie = #FF0000;
		colors.pie = #00FF00;
		colors.pie = #0000FF;
	 * String[] colors = config.getStringArray("colors.pie");
		List<Object> colorList = config.getList("colors.pie");
	 * @param configFile
	 * @param property
	 * @return
	 */
	public static List<Object> getList(String configFile, String property) {
		if (!configMap.containsKey(configFile)) {
			initor.initConfig(configFile);
		}
		PropertiesConfiguration config = (PropertiesConfiguration) configMap.get(configFile);
		List<Object> list = config.getList(property);
		return list;
	}
	
	
	
	/** 
	 * 载入配置文件，初始化后加入map 
	 * @param configFile 
	 */
	private synchronized void initConfig(String configFile) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(configFile);
			configMap.put(configFile, config);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}
}
