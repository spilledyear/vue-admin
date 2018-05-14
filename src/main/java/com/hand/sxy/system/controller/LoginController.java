package com.hand.sxy.system.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.system.dto.Result;
import com.hand.sxy.system.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * @author spilledyear
 * @date 2018/4/21 12:58
 */
@Controller
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/api/system/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, User user) {

        List<User> userList = loginService.login(user);
        Result result = new Result(userList);

        if (userList == null || userList.isEmpty()) {
            logger.info("登录失败，用户名或密码错误");
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
            return result;
        }

        logger.info("登录成功");
        return result;
    }

    @RequestMapping("/login")
    public String require(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        /**
         * 返回的内容就是templetes下面文件的名称
         * return new ModelAndView("loginPage", map);
         */
        return "login";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

        return "index";
    }
}
