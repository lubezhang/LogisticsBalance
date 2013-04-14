package com.lube.replenish.dao;

import com.lube.replenish.entity.TBalance;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午10:57
 * To change this template use File | Settings | File Templates.
 *
 * 快递单数据补录DAO
 */
@Repository("iReplenishDao")
@Transactional
public interface IReplenishDao {

    public void insertBalance(TBalance balance);

    @Deprecated
    public List<TBalance> queryAllBalance(TBalance entity);
    public List<TBalance> queryAllBalance(Map<String, Object> params);

    public int queryAllBalanceCount(Map<String, Object> params);

    public TBalance queryBalanceDetail(TBalance entity);

    public int updateBalance(TBalance entity);

    public TBalance queryNextBalance();

    public Map<String, Object> payMoney(TBalance entity);
    public Map<String, Object> notPayMoney(TBalance entity);

    void deleteBalance(String[] ids);
}
