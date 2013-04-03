package com.lube.user.service;

import com.lube.user.dao.IUserDao;
import com.lube.user.entity.Operator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午9:59
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
    private static Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Resource
    private IUserDao userDao;

    @Override
    public Operator verifyLogin(Operator operator) {
        List<Operator> list = userDao.verifyLogin(operator);
        return list.size() > 0?list.get(0):null;
    }
}
