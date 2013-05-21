package com.lube.replenish.service.impl;

import com.lube.common.CommonConst;
import com.lube.replenish.dao.IReplenishDao;
import com.lube.replenish.entity.TBalance;
import com.lube.replenish.service.IReplenishService;
import com.lube.user.entity.User;
import com.lube.utils.BalanceUtils;
import com.lube.utils.FileUtils;
import com.lube.utils.LogisticsException;
import com.lube.utils.WebUtils;
import com.lube.utils.excel.ExcelCellStyleUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-1-16
 * Time: 下午10:59
 * To change this template use File | Settings | File Templates.
 */

@Service("replenishService")
public class ReplenishServiceImp implements IReplenishService {
    private static Logger logger = Logger.getLogger(ReplenishServiceImp.class);

    @Resource
    private IReplenishDao iReplenishDao;

    /**
     * 初始化文件目录结构
     */
    private void initFilePath() {
        String rootPath = CommonConst.BALANCE_ROOT_PATH;

        File fileReady = new File(rootPath + File.separator + CommonConst.BALANCE_PIC_READY);
        if (!fileReady.exists()) {
            fileReady.mkdirs();
        }

        File fileComplete = new File(rootPath + File.separator + CommonConst.BALANCE_PIC_COMPLETE);
        if (!fileComplete.exists()) {
            fileComplete.mkdirs();
        }

        File fileError = new File(rootPath + File.separator + CommonConst.BALANCE_PIC_ERROR);
        if (!fileError.exists()) {
            fileError.mkdirs();
        }
    }

    @Override
    public List<String> getPicList() throws Exception {
        initFilePath();
        String rootPath = CommonConst.BALANCE_ROOT_PATH;
        File filePath = new File(rootPath + File.separator + CommonConst.BALANCE_PIC_READY);
        File[] files = filePath.listFiles();
        if (null == files) throw new LogisticsException("没有需要处理的图片！" + filePath);

        List<String> list = new ArrayList<String>(0);
        String fileName = "";
        for (File file : files) {
            if (file.isFile()) {
//                fileName = file.getName();
//                list.add(fileName.substring(0, fileName.indexOf(".")));
                list.add(file.getName());
            }
        }
        return list;
    }

    @Override
    public Map<String, String> importBalance() throws Exception {
        String rootPath = CommonConst.BALANCE_ROOT_PATH + File.separator;
        String strSrcPath = rootPath + CommonConst.BALANCE_PIC_READY + File.separator;

        List<String> picList = getPicList();
        TBalance balance = new TBalance();
        int completeCount = 0;
        int errorCount = 0;
        for (String picName : picList) {
            try {
                balance.setBalanceId(BalanceUtils.generatorUUID());
                balance.setBalanceCode(picName.substring(0, picName.indexOf(".")));
                balance.setGatherState("2");
                balance.setPayoffState("2");
                balance.setEdit("0");
                balance.setBalanceUser("");
                insertBalance(balance);
                String srcPath = strSrcPath + picName;
                String distPath = rootPath + CommonConst.BALANCE_PIC_COMPLETE + File.separator + picName;
                FileUtils.move(srcPath, distPath);
                completeCount++;
            } catch (Exception e) {
                String srcPath = strSrcPath + picName;
                String distPath = rootPath + CommonConst.BALANCE_PIC_ERROR + File.separator + picName;
                FileUtils.move(srcPath, distPath);
                errorCount++;
                logger.error("导入快递单异常：",e);
            }
        }

        Map<String, String> rsMap = new HashMap<String, String>(0);
        rsMap.put("completeCount", completeCount + "");
        rsMap.put("errorCount", errorCount + "");
        return rsMap;
    }

    @Override
    public void insertBalance(TBalance balance) throws Exception {
//        TBalance tb = new TBalance();
//        tb.setBalanceId(params.get("balanceId"));
//        tb.setBalanceCode(params.get("balanceCode"));
        iReplenishDao.insertBalance(balance);
    }

    @Override
    @Deprecated
    public List<Map<String, String>> queryAllBalance(TBalance entity) throws Exception {
        Map<String, Object> params = BalanceUtils.boToMapForBalance(entity);
        List<TBalance> list = iReplenishDao.queryAllBalance(params);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>(0);
        try {
            for (TBalance t : list) {
                Map map = BeanUtils.describe(t);
                map.put("picPath", "/" + CommonConst.BALANCE_PIC_CONTEXT + File.separator + CommonConst.BALANCE_PIC_COMPLETE + File.separator + t.getBalanceCode() + ".jpg");
                listMap.add(map);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return listMap;
    }

    @Override
    public List<Map<String, String>> queryAllBalance(Map<String, Object> params) throws Exception {
        int page = Integer.parseInt(params.get("page") + "");
        int pagesize = Integer.parseInt(params.get("pagesize") + "");
        params.put("page", (page - 1) * pagesize);
        params.put("pagesize", pagesize);
        List<TBalance> list = iReplenishDao.queryAllBalance(params);
        int count = iReplenishDao.queryAllBalanceCount(params);
        List<Map<String, String>> listMap = new ArrayList<Map<String, String>>(0);
        Map<String, String> entityMap = null;
        try {
            for (TBalance t : list) {
//                entityMap = new HashMap<String, String>(0);
                entityMap = BeanUtils.describe(t);
                entityMap.put("picPath", "/" + CommonConst.BALANCE_PIC_CONTEXT + File.separator + CommonConst.BALANCE_PIC_COMPLETE + File.separator + t.getBalanceCode() + ".jpg");
                listMap.add(entityMap);
            }
            Map tmp = new HashMap();
            tmp.put("count", count + "");
            listMap.add(tmp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return listMap;
    }

    @Override
    public int updateBalance(TBalance entity) throws Exception {
        return iReplenishDao.updateBalance(entity);
    }

    @Override
    public void exportLogistics(OutputStream output, Map<String, String> params) throws Exception {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("快递单对账");

        //标题行
        String[] titleName = new String[]{"序号", "订单编号", "客户名称", "收款状态", "应收金额"};
        HSSFRow titleRow = sheet.createRow(0);
        HSSFCellStyle titleStyle = ExcelCellStyleUtils.titleStyle(workbook);
        HSSFCell titleCell = null;
        for (int i = 0; i < titleName.length; i++) {
            titleCell = titleRow.createCell(i);
            titleCell.setCellStyle(titleStyle);
            titleCell.setCellType(HSSFCell.CELL_TYPE_STRING);
            titleCell.setCellValue(titleName[i]);
        }

        List<TBalance> list = iReplenishDao.queryAllBalance(new HashMap<String, Object>(params));
        HSSFCellStyle cellStyle = ExcelCellStyleUtils.nameStyle(workbook);
        HSSFRow row = null;
        for (int j = 0; j < list.size(); j++) {
            row = sheet.createRow((short) (j + 1));
            TBalance tBalance = list.get(j);

            HSSFCell cell0 = row.createCell(0);
            cell0.setCellStyle(cellStyle);
            cell0.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell0.setCellValue((j + 1));

            HSSFCell cell1 = row.createCell(1);
            cell1.setCellStyle(cellStyle);
            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell1.setCellValue(tBalance.getBalanceCode());

            HSSFCell cell2 = row.createCell(2);
            cell2.setCellStyle(cellStyle);
            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell2.setCellValue(tBalance.getCustomerId());

            HSSFCell cell3 = row.createCell(3);
            cell3.setCellStyle(cellStyle);
            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
            cell3.setCellValue(tBalance.getPayoffState());

            HSSFCell cell4 = row.createCell(4);
            cell4.setCellStyle(cellStyle);
            cell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell4.setCellValue(tBalance.getMoney());
        }

        row = sheet.createRow((short) (list.size() + 1));
        for (int k = 1; k < list.size() - 2; k++) {
            HSSFCell cell = row.createCell(k);
            cell.setCellStyle(cellStyle);
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue("");
        }

        HSSFCell cell0 = row.createCell(0);
        cell0.setCellStyle(cellStyle);
        cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
        cell0.setCellValue("应收金额");

        HSSFCell cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
        Map<String, Object> money2 = iReplenishDao.notPayMoney(BalanceUtils.mapToBalance(params));
        cell.setCellValue(null != money2 ? money2.get("totalMoney") + "" : "0");
        workbook.write(output);
    }

    @Override
    public Map<String, String> queryNextBalance(String userId) {
        Map<String, String> params = new HashMap<String, String>(0);
        params.put("lockUser", userId);
        TBalance bean = iReplenishDao.queryNextBalance(params);
        Map<String, String> entityMap = null;
        try {
            if(null == bean){
                throw new LogisticsException("没有需要补录的快递单！");
            }
            entityMap = new HashMap<String, String>(0);
            entityMap = BeanUtils.describe(bean);
            entityMap.put("picPath", "/" + CommonConst.BALANCE_PIC_CONTEXT + File.separator + CommonConst.BALANCE_PIC_COMPLETE + File.separator + bean.getBalanceCode() + ".jpg");
        } catch (Exception e) {
            entityMap = new HashMap<String, String>(0);
            logger.error(e.getMessage(), e);
        }
        return entityMap;
    }

    @Override
    public Map<String, String> getTotalMoney(TBalance balance) {
        Map<String, Object> money1 = iReplenishDao.payMoney(balance);
        Map<String, Object> money2 = iReplenishDao.notPayMoney(balance);

        Map<String, String> map = new HashMap<String, String>(0);
        map.put("payMoney", null != money1 ? money1.get("totalMoney") + "" : "0");
        map.put("notPayMoney", null != money2 ? money2.get("totalMoney") + "" : "0");
        return map;
    }

    @Override
    public Map<String, Object> queryBalanceDetail(TBalance entity) {
        TBalance obj = iReplenishDao.queryBalanceDetail(entity);
        Map<String, Object> entityMap = null;
        try {
            entityMap = BeanUtils.describe(obj);
            entityMap.put("picPath", "/" + CommonConst.BALANCE_PIC_CONTEXT + File.separator + CommonConst.BALANCE_PIC_COMPLETE + File.separator + obj.getBalanceCode() + ".jpg");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entityMap;
    }

    @Override
    public void deleteBalance(String[] ids) throws LogisticsException{
        iReplenishDao.deleteBalance(ids);
    }

    @Override
    public void lockBalance(String[] ids, User user) throws LogisticsException {
        Map<String, Object> params = new HashMap<String, Object>(0);
        params.put("ids", ids);
        params.put("userId", user.getOperatorId());
        iReplenishDao.lockBalance(params);
    }
}
