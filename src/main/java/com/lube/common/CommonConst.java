package com.lube.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-20
 * Time: 上午12:32
 * To change this template use File | Settings | File Templates.
 */
public class CommonConst {
    /** 授权是否成功的标记 */
    public static boolean validLicense = false;

    /** 授权文件路径 */
    public static String LICENSE_FILE_PATH = "C:/License";
    public static String LOCALHOST_INFO_FILE_PATH = "C:/LicenseInfo";

    /** 应用系统的根路径 */
    public static String BALANCE_ROOT_PATH = "";
    public static final String BALANCE_PIC_CONTEXT = "balancePic";
    public static final String BALANCE_PIC_READY = "ready";
    public static final String BALANCE_PIC_COMPLETE = "complete";
    public static final String BALANCE_PIC_ERROR = "error";

    /** 登录用户在Session的key */
    public static final String OPERATOR_SESSION_KEY = "operatorKey";


    /**  ET99加密狗异常代码的错误描述 */
    private static Map<Integer, String> map = new HashMap<Integer, String>(0);
    static{
        map = new HashMap<Integer, String>(0);
        map.put(0x00,"执行成功");
        map.put(0x01,"访问被拒绝，权限不够");
        map.put(0x02,"通讯错误，没有打开设备");
        map.put(0x03,"无效的参数，参数出错");
        map.put(0x04,"没有设备PID");
        map.put(0x05,"打开指定的设备失败");
        map.put(0x06,"硬件错误");
        map.put(0x07,"未知错误");
        map.put(0x0F,"验证PIN码掩码");
        map.put(0xFF,"验证PIN码错误且永远不锁死");
    }
    public static final Map<Integer, String> USER_KEY_ERROR = map;


    public static final String MESSAGE_SYS_LICENSE_VERIFY_FAILD = "系统授权校验失败";
    public static final String MESSAGE_SYS_LICENSE_VERIFY_SUCCESS = "系统授权校验通过";

}
