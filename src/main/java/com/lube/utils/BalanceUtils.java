package com.lube.utils;

import com.lube.replenish.entity.TBalance;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-18
 * Time: 下午10:34
 * To change this template use File | Settings | File Templates.
 *
 * 物流系统使用的一些处理工具
 */
public class BalanceUtils {

    /**
     * 生成不会重复的字符串
     * @return
     */
    public static String generatorUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 将快递单的对象转成Map对象
     * @param tBalance
     * @return
     */
    public static Map<String, Object> boToMapForBalance(TBalance tBalance){
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Class type = tBalance.getClass();
//            Map returnMap = new HashMap();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(tBalance, new Object[0]);
                    if (result != null) {
                        map.put(propertyName, result);
                    } else {
                        map.put(propertyName, "");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return map;
    }

    public static TBalance mapToBalance(Map<String, String> params){
        TBalance tBalance = new TBalance();
        try {
            BeanUtils.populate(tBalance, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tBalance;
    }

    public static void main(String[] args){
        //System.out.println(BalanceUtils.generatorUUID());
//        Map<String, String> map = new HashMap<String, String>(0);
//        map.put("customerId","你好");
//        System.out.println(BalanceUtils.mapToBalance(map));

        TBalance tBalance = new TBalance();
        tBalance.setCustomerId("CustomerId");
        tBalance.setBalanceCode("BalanceCode");
        System.out.println(BalanceUtils.boToMapForBalance(tBalance));
    }
}
