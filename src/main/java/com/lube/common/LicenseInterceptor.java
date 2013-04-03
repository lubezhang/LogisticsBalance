package com.lube.common;

import com.lube.user.entity.Operator;
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

    private String[] paths = {"/user/verifyLogin.do"};

    private boolean licenseFlag;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        logger.info("=============拦截请求，验证权限==============");
        boolean flag = true;
        if (CommonConst.validLicense) {
            String servletPath = request.getServletPath();
            logger.debug("请求地址："+servletPath);
            for(int i = 0; i < paths.length; i++){
                if(servletPath.equalsIgnoreCase(paths[i])){
                    return true;
                }
            }
            Operator obj = (Operator) request.getSession().getAttribute(CommonConst.OPERATOR_SESSION_KEY);
            if (null != obj) {
                flag = true;
            } else {
//                response.setContentType("application/json;charset=UTF-8");
//                response.setHeader("Content-Type","application/json;charset=UTF-8");
                //response.setHeader("Transfer-Encoding", "chunked");
//                Content-Type:application/json;charset=UTF-8

//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/json");
//                response.getWriter().write("{\"successful\":false,\"message\":\"授权验证失败\"}");
                response.sendRedirect("/login.html");
                flag = false;
            }
        } else {
            response.sendRedirect("/licenseError.html");
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
