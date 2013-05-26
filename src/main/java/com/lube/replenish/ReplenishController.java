package com.lube.replenish;

import com.lube.common.CommonConst;
import com.lube.utils.LigerUtils;
import com.lube.replenish.entity.TBalance;
import com.lube.replenish.service.IReplenishService;
import com.lube.user.entity.User;
import com.lube.utils.BalanceUtils;
import com.lube.utils.LogisticsException;
import com.lube.utils.WebUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-17
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("replenishController")
public class ReplenishController {
    private static Logger logger = Logger.getLogger(ReplenishController.class);

    @Resource
    private IReplenishService replenishService;

    /**
     * 数据格式 {success:true, message:"处理完成", resultValue:{completeCount:10, errorCount:10}}
     *
     * @param params
     * @return
     */
    @RequestMapping("importPic")
    public @ResponseBody Map<String, Object> importPic(@RequestParam Map<String, String> params){
        Map<String, Object> rsMap = new HashMap<String, Object>(0);
        Map<String,String> map = null;
        try {
            map = replenishService.importBalance();
            rsMap.put("success", true);
            rsMap.put("message", "处理完成！");
            rsMap.put("resultValue", map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            rsMap.put("success", false);
            rsMap.put("message", e.getMessage());
            rsMap.put("resultValue", map);
        }
        return rsMap;
    }


    @RequestMapping("queryBalanceList")
    public @ResponseBody Map<String, Object> queryBalanceList(@RequestParam Map<String, String> params,HttpServletRequest request){
//        TBalance entity = new TBalance();
//        entity.setBalanceCode(params.get("balanceCode"));
//        entity.setPayoffState(params.get("payoffState"));
//        entity.setGatherState(params.get("gatherState"));
//        entity.setCustomerId(params.get("customerId"));
//        entity.setAddrDateBegin(params.get("addrDateBegin"));
//        entity.setAddrDateEnd(params.get("addrDateEnd"));

        List<Map<String, String>> list = null;
        Map<String, Object> resBody = null;
        try {
            list = replenishService.queryAllBalance(new HashMap<String, Object>(params));
            String count = "";
            if(null != list && list.size() > 0){
                count = list.get(list.size()-1).get("count");
                list.remove(list.size()-1);
            }
            resBody = LigerUtils.resultList(list, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return resBody;
    }

    @RequestMapping("queryBalanceDetail")
    public @ResponseBody Map<String, Object> queryBalanceDetail(@RequestParam Map<String, String> params){
        TBalance entity = new TBalance();
        entity.setBalanceId(params.get("balanceId"));
        entity.setBalanceCode(params.get("balanceCode"));

        Map<String, Object> map = null;
        try {
            map = replenishService.queryBalanceDetail(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    @RequestMapping("queryNextDetail")
    public @ResponseBody Map<String, String> queryNextDetail(@RequestParam Map<String, String> params){
        TBalance entity = new TBalance();
        entity.setBalanceId(params.get("balanceId"));

        Map<String, String> map = null;
        try {
            map = replenishService.queryNextBalance(WebUtils.getCurrUser().getOperatorId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    @RequestMapping("updateBalanceDetail")
    public @ResponseBody Map<String, String> updateBalanceDetail(@RequestParam Map<String, String> params,@ModelAttribute TBalance balance, HttpServletRequest request) throws Exception{
        Map<String ,String> map = new HashMap<String, String>(0);
        try{
            TBalance entity = new TBalance();
            entity.setBalanceId(params.get("balanceId"));
            entity.setBalanceCode(params.get("balanceCode"));
            entity.setCustomerId(params.get("customerId"));
            entity.setGatherState(params.get("gatherState"));
            entity.setBalanceUser(params.get("balanceUser"));
            entity.setMoney(Float.parseFloat(params.get("money")));
            User user = (User) request.getSession().getAttribute(CommonConst.OPERATOR_SESSION_KEY);
            entity.setOperatorId(user.getOperatorId());
            entity.setPayoffState(params.get("payoffState"));
            entity.setAddresseeDate(params.get("addresseeDate"));
            int rs = replenishService.updateBalance(entity);

            if(rs > 0){
//                map.put("success","true");
//                map.put("message","Message sent successfully.");
                LigerUtils.resultSuccess("Message sent successfully");
            } else {
//                map.put("success","false");
//                map.put("message","Message sent unsuccessfully.");
                LigerUtils.resultSuccess("Message sent unsuccessfully");
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    @RequestMapping("exportLogistics")
    public void exportLogistics(@RequestParam Map<String, String> params, HttpServletResponse response){
        OutputStream out = null;
        try {
            String strfileName = URLEncoder.encode("对账单.xls", "UTF-8");
            response.setHeader("Content-disposition","attachment; filename=" + strfileName);
//            response.setContentLength((int) strfileName.length());
            response.setContentType("application/x-download");
            out = response.getOutputStream();
            replenishService.exportLogistics(out, params);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if(null != out){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        return null;
    }

    @RequestMapping("getTotalMoney")
    public @ResponseBody Map<String, Object> getTotalMoney(@RequestParam Map<String, String> params){
        Map<String, Object> rsMap = new HashMap<String, Object>(0);
        Map<String,String> map = null;
        try {
            map = replenishService.getTotalMoney(BalanceUtils.mapToBalance(params));
            rsMap.put("success", true);
            rsMap.put("message", "处理完成！");
            rsMap.put("resultValue", map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            rsMap.put("success", false);
            rsMap.put("message", e.getMessage());
            rsMap.put("resultValue", map);
        }
        return rsMap;
    }

    @RequestMapping("deleteBalance")
    public @ResponseBody Map<String, Object> deleteBalance(String[] ids){
        Map<String, Object> rsMap = null;
        try {
            replenishService.deleteBalance(ids);
            rsMap = LigerUtils.resultSuccess("删除快递单成功！");
        } catch (LogisticsException e) {
            logger.error(e);
            rsMap = LigerUtils.resultFail("删除快递单失败！");
        }
        return rsMap;
    }

    /**
     * 当前登录用户锁定需要补录的快递单，快递单被锁定后不能被其他人编辑。
     * @param ids 需要锁定的快递单主键集合
     * @return
     */
    @RequestMapping("lockBalance")
    public @ResponseBody Map<String, Object> lockBalance(String[] ids){
        Map<String, Object> rsMap = null;
        try {
            User user = WebUtils.getCurrUser();
            replenishService.lockBalance(ids, user);
            rsMap = LigerUtils.resultSuccess("锁定快递单成功！");
        } catch (LogisticsException e) {
            logger.error(e);
            rsMap = LigerUtils.resultFail("锁定快递单失败！");
        }
        return rsMap;
    }

    /**
     *
     * @param ids
     * @return
     */
    @RequestMapping("updatePayOff")
    public @ResponseBody Map<String, Object> updatePayOff(String[] ids){
        Map<String, Object> rsMap = null;
        try {
            replenishService.updatePayOff(ids);
            rsMap = LigerUtils.resultSuccess("结算快递单成功！");
        } catch (LogisticsException e) {
            logger.error(e);
            rsMap = LigerUtils.resultFail("结算快递单失败！");
        }
        return rsMap;
    }
}
