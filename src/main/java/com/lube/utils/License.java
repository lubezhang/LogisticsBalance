package com.lube.utils;

import com.lube.common.CommonConst;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午2:20
 * To change this template use File | Settings | File Templates.
 */
public class License {
    private static Logger logger = Logger.getLogger(License.class);

    /**
     * 检查授权是否合法
     * {"IP":"192.168.1.103","hostName":"zhangqh-PC","mac":"00-21-5d-1f-a1-40"}
     * @return
     */
    public static boolean verifyLicense(){
        boolean licFlag = false;
        try {
            byte[] decodedData = decryptByPublicKey(FileUtils.readFileToBytes(CommonConst.LICENSE_FILE_PATH), getPublicKey());
            JSONObject jsonObject = JSONObject.fromObject(new String(decodedData));

            //机器名是否有效
            boolean hostNameFlag = LicenseUtils.getHostName().equalsIgnoreCase((String) jsonObject.get("hostName"));
            if(!hostNameFlag){
                throw new LogisticsException("机器名无效！");
            }

            //是否已经超过试用日期
            Date currDate = DateUtils.parseDate(DateUtils.getCurrentDate());
            Date expireDate = DateUtils.parseDate(String.valueOf(jsonObject.get("expireDate")));
            boolean expireDateFlag = currDate.before(expireDate);
            if(!expireDateFlag){
                throw new LogisticsException("超出使用期限！");
            }

            //校验MAC地址
            boolean MACFlag = LicenseUtils.getHostMac().equalsIgnoreCase(String.valueOf(jsonObject.get("MAC")));
            if(!MACFlag){
                throw new LogisticsException("Mac地址无效！");
            }

            if(hostNameFlag && expireDateFlag && MACFlag){
                licFlag = true;
            } else {
                throw new Exception("授权无效！");
            }
        } catch (Exception e) {
            licFlag = false;
            createHostInfo();
            logger.error(e.getMessage(),e);
        }
        return licFlag;
    }

    /**
     * 生成使用公钥加密的本机信息
     */
    public static void createHostInfo(){
        try {
            byte[] bytes = LicenseUtils.createLicenseString().getBytes();
            byte[] encodedData = encryptByPublicKey(bytes, getPublicKey());
            FileUtils.writeBytesToFile(CommonConst.LOCALHOST_INFO_FILE_PATH,encodedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * 获取公钥
     * @return
     */
    private static String getPublicKey(){
        StringBuilder publicKeyBuf = new StringBuilder();
        publicKeyBuf.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC0hdBJOdtER0QlnPz2D1NfNmSrwulzSrn4sDb");
        publicKeyBuf.append("PWBQiMTl7pmAvVmBVuxPWS1ocfCBOrGNrSpGnzxhQS0dQOwmAW9dnvQ8YYFb+JPsvOGJMi/wpllh");
        publicKeyBuf.append("RD2eavQ1wL2+7V/7OqDwz6HcH21NVRA8Ce7nJsDWiD75fDZwW4N47DaJ5QIDAQAB");
        return publicKeyBuf.toString();
    }

    public static void main(String[] args){
        License.verifyLicense();
    }
}
