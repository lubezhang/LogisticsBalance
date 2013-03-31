package com.lube.user.service;

import com.lube.user.entity.Operator;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-31
 * Time: 下午9:58
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {

    /**
     * 校验用户登录信息的有效性
     * @param operator
     * @return
     */
    boolean verifyLogin(Operator operator);
}
