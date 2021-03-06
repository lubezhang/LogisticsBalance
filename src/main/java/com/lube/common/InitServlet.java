package com.lube.common;

import com.lube.common.license.ILicense;
import com.lube.common.license.UsbKeyLicense;
import com.lube.replenish.service.impl.ReplenishServiceImp;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午2:25
 * To change this template use File | Settings | File Templates.
 */
public class InitServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(InitServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {

        CommonConst.BALANCE_ROOT_PATH = config.getServletContext().getRealPath("/") + CommonConst.BALANCE_PIC_CONTEXT;

        logger.info("=====检查系统授权====");
        ILicense license = new UsbKeyLicense();
        if(license.verifyLicense()){
//        if(true){
            ReplenishServiceImp.initFilePath();
            CommonConst.validLicense = true;
            logger.info("=====通过系统授权验证，系统正常启动！====");
        } else {
            CommonConst.validLicense = false;
            logger.error("=====系统授权无效，系统终止启动！====");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
