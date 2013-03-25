package com.lube.replenish.service.impl;

import com.lube.replenish.entity.TBalance;
import com.lube.replenish.service.IReplenishService;
import com.lube.utils.BalanceUtils;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午11:11
 * To change this template use File | Settings | File Templates.
 */
public class TestReplenishServiceImp {
    private ApplicationContext context;

    @Before
    public void before(){
        context = new ClassPathXmlApplicationContext("dispatcher-servlet.xml");
    }

    public void testGetPicList() throws Exception {
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        List<String> list = replenishService.getPicList();
        Assert.assertNotNull(list);
        Assert.assertTrue(0 != list.size());
        System.out.println(list);
    }

    public void testImportBalance() throws Exception{
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        replenishService.importBalance();
    }

    public void testInsertBalance() throws Exception {
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        List<String> list = replenishService.getPicList();
        TBalance entity = new TBalance();
        for (int i = 0; i < list.size(); i++){
            entity.setBalanceId(BalanceUtils.generatorUUID());
            entity.setBalanceCode(list.get(i));
            replenishService.insertBalance(entity);
        }
    }

    public void testQueryAll() throws Exception {
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        TBalance entity = new TBalance();
//        entity.setBalanceCode("301409475477");
        List<Map<String, String>> list = replenishService.queryAllBalance(entity);
        Assert.assertNotNull(list);
        Assert.assertTrue(0 != list.size());
    }

    public void testExportLogistics(){
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("c:\\test.xls");
            replenishService.exportLogistics(fileOutputStream, new HashMap<String, String>());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(null != fileOutputStream){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testUpdateBalance(){
        IReplenishService replenishService = context.getBean("replenishService", ReplenishServiceImp.class);
        TBalance balance = new TBalance();
        balance.setCustomerId("你好");
        balance.setBalanceId("8c03ace9755640df98c4b6ba858e3038");
        try {
            replenishService.updateBalance(balance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
