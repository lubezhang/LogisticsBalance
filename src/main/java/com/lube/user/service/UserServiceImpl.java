package com.lube.user.service;

import com.lube.user.entity.Menu;
import com.lube.utils.MD5EncryptUtils;
import com.lube.user.dao.IUserDao;
import com.lube.user.entity.User;
import com.lube.utils.BalanceUtils;
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
            user.setPassword(MD5EncryptUtils.MD5Encode(user.getPassword()));
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

    @Override
    public void addUser(Map<String, String> params) throws LogisticsException {
        try{
            params.put("operatorId", BalanceUtils.generatorUUID());
            params.put("password", MD5EncryptUtils.MD5Encode(params.get("password")));
            userDao.addUser(params);
            params.put("loginId", params.get("username"));
            userDao.addUserRole(params);
        } catch (Exception e){
            throw new LogisticsException("添加用户异常：",e);
        }
    }

    @Override
    public void checkUserName(String userName) throws LogisticsException {
        User user = new User();
        user.setUsername(userName);
        User rsUser = userDao.verifyLogin(user);
        if(null != rsUser){
            throw new LogisticsException("用户名已经存在");
        }
    }

    @Override
    public void deleteUser(String[] ids) throws LogisticsException {
        try{
            userDao.deleteUserRole(ids);
            userDao.deleteUser(ids);
        } catch(Exception e){
            throw new LogisticsException(e);
        }
    }

    @Override
    public List<Menu> queryUserMenu(String id) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("loginId",id);
        List<Menu> mainMenuList = userDao.queryUserChildMenu(params);
        List<Menu> childMenuList = null;
        for(int i = 0; i < mainMenuList.size(); i++){
            params.put("parentCode",mainMenuList.get(i).getCode());
            childMenuList = userDao.queryUserChildMenu(params);
            mainMenuList.get(i).setChildMenu(childMenuList);
        }
        return mainMenuList;
    }
}
