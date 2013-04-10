package com.lube.user;

import com.lube.common.CommonConst;
import com.lube.common.LigerUtils;
import com.lube.user.entity.User;
import com.lube.user.service.IUserService;
import com.lube.utils.LogisticsException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping("login")
    public String login() throws Exception {
        return "index";
    }

    /**
     *
     * 用户登录验证
     * @return
     */
    @RequestMapping("verifyLogin")
    public @ResponseBody Map<String, String> verifyLogin(@ModelAttribute User user, HttpServletRequest request){
        logger.info("===========检查用户登录信息==========");
        logger.debug(user);
        Map<String, String> map = new HashMap<String, String>();
        User oper = null;
        try {
            oper = userService.verifyLogin(user);
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

    @RequestMapping("queryUserList")
    public @ResponseBody Map<String, Object> queryUserList(@RequestParam Map<String, String> params){
        Map<String, Object> resBody = null;
        try {
            List<User> list = userService.queryAllUser(params);
            String count = String.valueOf(userService.queryAllUserCount(params));
            resBody = LigerUtils.resultMap(list, count);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return resBody;
    }

}
