package com.lube.user.dao;

import com.lube.user.entity.Operator;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
public interface IUserDao {

    List<Operator> verifyLogin(Operator operator);

}
