package com.lube.replenish.service;

import com.lube.replenish.entity.TBalance;
import com.lube.user.entity.User;
import com.lube.utils.LogisticsException;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午10:58
 * To change this template use File | Settings | File Templates.
 */
public interface IReplenishService {
    /**
     * 获取快递单扫描图片列表信息
     * @return
     * @throws Exception
     */
    public List<String> getPicList(String filePath) throws Exception;

    /**
     * 将快递单图片信息导入到数据库中
     * @return
     * @throws Exception
     */
    public Map<String,String> importBalance(Map<String, String> params) throws Exception;

    /**
     * 插入一条快递单信息
     * @param entity
     * @throws Exception
     */
    public void insertBalance(TBalance entity) throws Exception;

    /**
     * 快递单查询
     * @param entity 查询条件
     * @return
     * @throws Exception
     */
    List<Map<String, String>> queryAllBalance(TBalance entity) throws Exception;
    List<Map<String, String>> queryAllBalance(Map<String, Object> params) throws Exception;

    /**
     * 更新
     * @param entity
     * @return
     * @throws Exception
     */
    public int updateBalance(TBalance entity) throws Exception;

    public void exportLogistics(OutputStream output, Map<String, String> params) throws Exception;

    /**
     * 随机获取一个快递单信息
     * @return
     */
    Map<String, String> queryNextBalance(String userId);

    Map<String, String> getTotalMoney(TBalance balance);

    Map<String, Object> queryBalanceDetail(TBalance entity);

    void deleteBalance(String[] ids) throws LogisticsException;

    void lockBalance(String[] ids, User user) throws LogisticsException;
    void unlockBalance(String[] ids, User user) throws LogisticsException;

    void updatePayOff(String[] ids) throws LogisticsException;
}
