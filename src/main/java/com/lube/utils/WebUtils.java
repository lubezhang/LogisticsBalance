package com.lube.utils;

import com.lube.common.CommonConst;
import com.lube.user.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-20
 * Time: 下午11:39
 * To change this template use File | Settings | File Templates.
 */
public class WebUtils {
    /**
     * 获取当前系统中的Session
     * @return
     */
    public static HttpSession getSession(){
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr == null ? null : attr.getRequest().getSession(true);
        return session;
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static User getCurrUser(){
        return (User) getSession().getAttribute(CommonConst.OPERATOR_SESSION_KEY);
    }
}
