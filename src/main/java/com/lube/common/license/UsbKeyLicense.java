package com.lube.common.license;

import ET299jni.CET299;
import ET299jni.ET299Def;
import ET299jni.IET299;
import ET299jni.RTException;
import com.lube.common.CommonConst;
import com.lube.utils.LogisticsException;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-26
 * Time: 下午3:25
 *
 * 使用USB加密狗方式对系统授权
 */
public class UsbKeyLicense implements ILicense {
    private static Logger logger = Logger.getLogger(UsbKeyLicense.class);

    private IET299 et99 = new CET299();

    private String strPid = "8FC5E436";
    private String strUserPin = "FFFFFFFFFFFFFFFF";

    private byte[] pid = strPid.getBytes();
    private byte[] userPin = strUserPin.getBytes();

    int[] keyCount = new int[1];

    @Override
    public boolean verifyLicense() {
        logger.info("=============USB Key 授权==============");
        boolean verifyFlag = false;
        try {
            openUsbKey();
            verifyFlag = true;
        } catch (LogisticsException e) {
            logger.error(e);
        } finally {
            et99.CloseToken();
        }
        return verifyFlag;
    }

    /**
     * 使用普通用户模式打开USB Key，用户模式只能读取key中的数据，不能修改key中保存的数据。
     */
    private void openUsbKey() throws LogisticsException{
        try{
            et99.FindToken(pid, keyCount);
            et99.OpenToken(pid, 1);
            et99.Verify(ET299Def.ET_VERIFY_USERPIN, userPin);
            logger.debug("打开USB KEY成功");
        } catch (RTException e){
            throw new LogisticsException("打开USB KEY失败："+CommonConst.USER_KEY_ERROR.get(e.HResult()));
        }
    }

    /**
     * 读取USB KEY内保存的授权信息
     * @throws LogisticsException
     */
    private void readKeyData() throws LogisticsException{
        try{
            logger.debug("读取USB KEY数据成功");
        } catch (RTException e){
            throw new LogisticsException("读取USB KEY数据失败："+CommonConst.USER_KEY_ERROR.get(e.HResult()));
        }
    }

    public static void main(String[] args){
        ILicense license = new UsbKeyLicense();
        if(license.verifyLicense()){
            System.out.println("系统授权校验通过！");
        } else {
            System.out.println("系统授权校验失败！");
        }
    }
}
