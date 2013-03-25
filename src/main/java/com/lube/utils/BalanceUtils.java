package com.lube.utils;

import com.lube.replenish.entity.TBalance;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
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

    public static Map<String, Object> boToMapForBalance(TBalance tBalance){
        return new HashMap<String, Object>();
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
        Map<String, String> map = new HashMap<String, String>(0);
        map.put("customerId","你好");
        System.out.println(BalanceUtils.mapToBalance(map));
    }
}
