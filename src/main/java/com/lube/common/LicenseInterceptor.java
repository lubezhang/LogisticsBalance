package com.lube.common;

import com.lube.common.license.ILicense;
import com.lube.common.license.UsbKeyLicense;
import com.lube.user.entity.User;
import com.lube.utils.LigerUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;
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
        boolean flag = false;

        ILicense license = new UsbKeyLicense();
        if (license.verifyLicense()) {
            logger.debug("校验授权成功！");
            String servletPath = request.getServletPath();
            logger.info("请求地址：" + servletPath);
            for(int i = 0; i < paths.length; i++){
                if(servletPath.equalsIgnoreCase(paths[i])){
                    return true;
                }
            }
            User obj = (User) request.getSession().getAttribute(CommonConst.OPERATOR_SESSION_KEY);
            if (null != obj) {

                flag = true;
            } else {
                logger.debug("用户登录超时，请重新登录！");
//                response.setContentType("application/json;charset=UTF-8");
//                response.setHeader("Content-Type","application/json;charset=UTF-8");
                //response.setHeader("Transfer-Encoding", "chunked");
//                Content-Type:application/json;charset=UTF-8

//                response.setCharacterEncoding("UTF-8");
//                response.setContentType("text/json");
//                response.getWriter().write("{\"successful\":false,\"message\":\"授权验证失败\"}");
                response.sendRedirect("/login.html");
            }
        } else {
            logger.debug("校验授权失败！");
//            response.sendRedirect("/licenseError.html");
//            LigerUtils.resultMeaage(false, "校验授权失败", "90");
            response.setContentType("application/json;charset=UTF-8");
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setHeader("Transfer-Encoding", "Content-Type:application/json;charset=UTF-8");

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/json");
            response.getWriter().write("{\"success\":false,\"message\":\""+CommonConst.MESSAGE_SYS_LICENSE_VERIFY_FAILD+"\", \"returnCode\":\"90\"}");
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
