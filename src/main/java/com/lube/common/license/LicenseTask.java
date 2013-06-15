package com.lube.common.license;

import com.lube.common.CommonConst;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-15
 * Time: 下午4:26
 * To change this template use File | Settings | File Templates.
 */

@Service
public class LicenseTask {
    private static Logger logger = Logger.getLogger(LicenseTask.class);

    public void verifyLicense(){
        logger.info("======定时检查授权=====");
        ILicense license = new UsbKeyLicense();
        CommonConst.validLicense = license.verifyLicense();
    }
}
