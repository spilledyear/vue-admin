package com.hand.sxy.system.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.system.dto.Result;
import com.hand.sxy.system.service.ILoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 12:58
 */
@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private ILoginService loginService;

    @RequestMapping(value = "/api/system/login", method = RequestMethod.POST)
    public Result login(HttpServletRequest request, @RequestBody User user) {

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

    @RequestMapping(value = "/login")
    public void login(HttpServletResponse response, @RequestBody User user) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("{\"status\":\"error\",\"msg\":\"尚未登录，请登录!\"}");
        out.flush();
        out.close();
    }

}
