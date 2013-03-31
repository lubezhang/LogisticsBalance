package com.lube.common;

import com.lube.utils.License;
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
        logger.info("=====检查License是否合法====");
        try {
            if(License.verifyLicense()){
                CommonConst.validLicense = true;
            } else {
                throw new Exception("系统授权无效！");
            }
        } catch (Exception e){
            CommonConst.validLicense = false;
            System.out.println("=====系统授权无效，系统终止启动！====");
            System.exit(0);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}