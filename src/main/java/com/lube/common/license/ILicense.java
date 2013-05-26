package com.lube.common.license;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-5-26
 * Time: 下午3:22
 * To change this template use File | Settings | File Templates.
 */
public interface ILicense {
    /**
     * 检查授权是否合法
     * @return true:授权合法；false：授权非法
     */
    boolean verifyLicense();
}
