package com.lube.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-4-11
 * Time: 上午12:21
 * To change this template use File | Settings | File Templates.
 */
public class LigerUtils {
    public static Map<String, Object> resultMap(List rows, String total){
        Map<String, Object> resBody = new HashMap<String, Object>(0);
        resBody.put("Total", total);
        resBody.put("Rows", rows);

        return resBody;
    }
}
