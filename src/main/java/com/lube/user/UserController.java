package com.lube.user;

import com.lube.user.entity.Operator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-10
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("userController")
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    /**
     *
     * 用户登录验证
     * @return
     */
    @RequestMapping("login")
    public @ResponseBody Map<String, Object> login(ModelMap modelMap){
        Object o = modelMap.get("operator4");
        Operator operator = new Operator();
        operator.setOperatorName("zhangqh");
        System.out.println(operator);
        modelMap.put("operator",operator);
        logger.info("===========用户登录==========");
        return new HashMap<String, Object>();
    }

    @RequestMapping("getLoginInfo")
    public @ResponseBody Map<String, Object> getLoginInfo(@RequestParam Map<String, String> operator3){
        System.out.println("获取登录用户："+operator3);
        return new HashMap<String, Object>();
    }
}
