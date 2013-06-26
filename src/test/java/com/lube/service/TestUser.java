package com.lube.service;

import com.lube.user.dao.IUserDao;
import com.lube.user.entity.Menu;
import com.lube.user.service.IUserService;
import com.lube.utils.LogisticsException;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-6-23
 * Time: 下午3:55
 * To change this template use File | Settings | File Templates.
 */
public class TestUser {
    private ApplicationContext context;

    @Before
    public void before(){
        context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
    }

    @Test
    public void testAllMenu(){
        IUserService userDao = context.getBean("userService", IUserService.class);
        List<Menu> list = null;
        try {
            list = userDao.queryUserMenu("admin");
        } catch (LogisticsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size() != 0);
        System.out.println(list);
    }
}
