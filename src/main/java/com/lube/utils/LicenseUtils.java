package com.lube.utils;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午5:00
 * To change this template use File | Settings | File Templates.
 */
public class LicenseUtils {

    /**
     * 获取本机IP
     * @return
     */
    public static String getHostIP(){
        String strIP = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            strIP  = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return strIP;
    }

    /**
     * 获取本机机器名
     * @return
     */
    public static String getHostName(){
        String strHostName = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            strHostName  = inetAddress.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return strHostName;
    }

    /**
     * 获取本机的MAC地址
     * @return
     */
    public static String getHostMac(){
        String strMac = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(inetAddress);
            StringBuilder macBuff = new StringBuilder();
            byte[] byteMac  = ni.getHardwareAddress();
            String mac = "";
            for(int i = 0; i < byteMac.length; i++){
                mac = Integer.toHexString(byteMac[i] & 0xFF);
                if (mac.length() == 1){
                    mac = '0' + mac;
                }
                if(0 == i){
                    macBuff.append(mac);
                } else {
                    macBuff.append("-" + mac);
                }
            }
            strMac = macBuff.toString();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return strMac;
    }

    /**
     * 创建需要校验的本机信息
     * @return
     */
    public static String createLicenseString(){
        Map<String, String> licMap = new HashMap<String,String>();
        licMap.put("IP",LicenseUtils.getHostIP());
        licMap.put("hostName", LicenseUtils.getHostName());
        licMap.put("mac", LicenseUtils.getHostMac());
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(licMap);
        return jsonObject.toString();
    }

    public static void main(String[] args){
        System.out.println(LicenseUtils.getHostIP());
        System.out.println(LicenseUtils.getHostName());
        System.out.println(LicenseUtils.getHostMac());
        System.out.println(LicenseUtils.createLicenseString());
    }

}
