package com.lube.user;

import com.lube.common.CommonConst;
import com.lube.user.entity.Operator;
import com.lube.user.service.IUserService;
import com.lube.utils.LogisticsException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhangqh
 * Date: 13-3-10
 * Time: 上午11:22
 * To change this template use File | Settings | File Templates.
 */

@Controller
@RequestMapping("user")
public class UserController {
    private static Logger logger = Logger.getLogger(UserController.class);

    @Resource
    private IUserService userService;

    /**
     *
     * 用户登录验证
     * @return
     */
    @RequestMapping("verifyLogin")
    public @ResponseBody Map<String, String> verifyLogin(@ModelAttribute Operator operator, HttpServletRequest request){
        logger.info("===========检查用户登录信息==========");
        logger.debug(operator);
        Map<String, String> map = new HashMap<String, String>();
        Operator oper = null;
        try {
            oper = userService.verifyLogin(operator);
            if(null != oper){
                request.getSession().setAttribute(CommonConst.OPERATOR_SESSION_KEY, oper);
                map.put("success","true");
            } else {
                map.put("success","false");
                map.put("message","登录失败，请检查用户名和密码！");
            }
        } catch (LogisticsException e) {
            map.put("success","false");
            map.put("message","登录失败，系统异常！");
        }

        return map;
    }

    @RequestMapping("login")
    public String login() throws Exception {
        return "index";
    }
}
