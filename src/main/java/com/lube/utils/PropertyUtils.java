package com.lube.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-3
 * Time: 下午8:56
 * To change this template use File | Settings | File Templates.
 */
public class PropertyUtils {
    private static Logger logger = Logger.getLogger(PropertyUtils.class);
    private static final String PROP_FILE_NAME = "config.properties";
    private static Properties prop = null;

    public static void getInstance() {
        InputStream in = null;
        try {
            if (null == prop) {
                prop = new Properties();
                in = PropertyUtils.class.getClassLoader().getResourceAsStream(PROP_FILE_NAME);
                prop.load(in);
            }
        } catch (Exception e) {
            prop = null;
            logger.error("初始化配置文件异常：", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将属性文件的配置信息转换成Map对象，
     * Map对象的key与属性文件的key对应
     *
     * @return
     */
    public static Map<String, String> propToMap() {
        getInstance();
        Map<String, String> map = new HashMap<String, String>();

        Enumeration enu = prop.propertyNames();
        while (enu.hasMoreElements()) {
            String key = String.valueOf(enu.nextElement());
            map.put(key, prop.getProperty(key));
        }

        return map;
    }


    public static String getProperty(String key) {
        return propToMap().get(key);
    }

    public static void main(String[] args) {
        System.out.println(getProperty("picPath"));
    }
}
