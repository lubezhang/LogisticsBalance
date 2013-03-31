package com.lube.common;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-9
 * Time: 上午8:23
 * To change this template use File | Settings | File Templates.
 */
public class LicenseInterceptor implements HandlerInterceptor {
    private static Logger logger = Logger.getLogger(LicenseInterceptor.class);

    private boolean licenseFlag;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("=============拦截请求，验证权限==============");
        boolean flag = true;
        String strLic = "";
        if (licenseFlag) {
            if (CommonConst.validLicense) {
                flag = true;
            } else {
//                response.setContentType("application/json;charset=UTF-8");
//                response.setHeader("Content-Type","application/json;charset=UTF-8");
                //response.setHeader("Transfer-Encoding", "chunked");
//                Content-Type:application/json;charset=UTF-8

//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/json");
//                response.getWriter().write("{\"successful\":false,\"message\":\"授权验证失败\"}");
//                flag = false;
                response.sendRedirect("/licenseError.html");
            }
        }
        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        logger.info("---------------postHandle--------------");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        logger.info("++++++++++++++afterCompletion++++++++++++++");
    }

    public boolean isLicenseFlag() {
        return licenseFlag;
    }

    public void setLicenseFlag(boolean licenseFlag) {
        this.licenseFlag = licenseFlag;
    }
}
