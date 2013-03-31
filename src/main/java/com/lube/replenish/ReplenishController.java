package com.lube.replenish;

import com.lube.replenish.entity.TBalance;
import com.lube.replenish.service.IReplenishService;
import com.lube.utils.BalanceUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
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
     * 数据格式 {successful:true, message:"处理完成", resultValue:{completeCount:10, errorCount:10}}
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
            rsMap.put("successful", true);
            rsMap.put("message", "处理完成！");
            rsMap.put("resultValue", map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            rsMap.put("successful", false);
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
        Map<String, Object> resBody = new HashMap<String, Object>(0);
        try {
            list = replenishService.queryAllBalance(new HashMap<String, Object>(params));
            String count = "";
            if(null != list && list.size() > 0){
                count = list.get(list.size()-1).get("count");
                list.remove(list.size()-1);
            }
            resBody.put("total", count);
            resBody.put("rows", list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return resBody;
    }

    @RequestMapping("queryBalanceDetail")
    public @ResponseBody Map<String, String> queryBalanceDetail(@RequestParam Map<String, String> params){
        TBalance entity = new TBalance();
        entity.setBalanceId(params.get("balanceId"));
        entity.setBalanceCode(params.get("balanceCode"));

        List<Map<String, String>> list = new ArrayList<Map<String, String>>(0);
        try {
            list = replenishService.queryAllBalance(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return list.get(0);
    }

    @RequestMapping("queryNextDetail")
    public @ResponseBody Map<String, String> queryNextDetail(@RequestParam Map<String, String> params){
        TBalance entity = new TBalance();
        entity.setBalanceId(params.get("balanceId"));

        Map<String, String> map = null;
        try {
            map = replenishService.queryNextBalance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return map;
    }

    @RequestMapping("updateBalanceDetail")
    public @ResponseBody Map<String, String> updateBalanceDetail(@RequestParam Map<String, String> params) throws Exception{
        Map<String ,String> map = new HashMap<String, String>(0);
        try{
            TBalance entity = new TBalance();
            entity.setBalanceId(params.get("balanceId"));
            entity.setBalanceCode(params.get("balanceCode"));
            entity.setCustomerId(params.get("customerId"));
            entity.setGatherState(params.get("gatherState"));
            entity.setMoney(Float.parseFloat(params.get("money")));
            entity.setOperatorId(params.get("operatorId"));
            entity.setPayoffState(params.get("payoffState"));
            entity.setAddresseeDate(params.get("addresseeDate"));
            int rs = replenishService.updateBalance(entity);

            if(rs > 0){
                map.put("successful","true");
                map.put("message","Message sent successfully.");
            } else {
                map.put("successful","false");
                map.put("message","Message sent unsuccessfully.");
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
            rsMap.put("successful", true);
            rsMap.put("message", "处理完成！");
            rsMap.put("resultValue", map);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            rsMap.put("successful", false);
            rsMap.put("message", e.getMessage());
            rsMap.put("resultValue", map);
        }
        return rsMap;
    }
}
