package com.lube.user.dao;

import com.lube.user.entity.Menu;
import com.lube.user.entity.User;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
public interface IUserDao {

    User verifyLogin(User user);
    List<User> queryAllUser(Map<String, Object> params);
    int queryAllUserCount(Map<String, Object> params);

    void addUser(Map<String,String> params);

    void deleteUser(String[] ids);

    List<Menu> queryUserChildMenu(Map<String, Object> params);
}
