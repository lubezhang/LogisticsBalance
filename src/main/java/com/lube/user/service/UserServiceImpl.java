package com.lube.user.service;

import com.lube.user.dao.IUserDao;
import com.lube.user.entity.User;
import com.lube.utils.LogisticsException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public User verifyLogin(User user) throws LogisticsException {
        User rsUser;
        try{
            rsUser = userDao.verifyLogin(user);
//            user = list.size() > 0?list.get(0):null;
        }catch (Exception e){
            logger.error(e);
            throw new LogisticsException(e);
        }
        return rsUser;
    }

    @Override
    public List<User> queryAllUser(Map<String, String> params) {
        Map<String,Object> par = new HashMap<String, Object>(params);
        int page = Integer.parseInt(params.get("page"));
        int pagesize = Integer.parseInt(params.get("pagesize"));
        par.put("page", (page - 1) * pagesize);
        par.put("pagesize", pagesize);
        return userDao.queryAllUser(par);
    }

    @Override
    public int queryAllUserCount(Map<String, String> params) {
        Map<String,Object> par = new HashMap<String, Object>(params);
        int page = Integer.parseInt(params.get("page"));
        int pagesize = Integer.parseInt(params.get("pagesize"));
        par.put("page", (page - 1) * pagesize);
        par.put("pagesize", pagesize);
        return userDao.queryAllUserCount(par);
    }


}
