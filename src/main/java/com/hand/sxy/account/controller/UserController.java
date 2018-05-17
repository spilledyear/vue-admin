package com.hand.sxy.account.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.service.IUserSrvice;
import com.hand.sxy.system.dto.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:32
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserSrvice userSrvice;


    /**
     * 查询用户集合
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResultResponse query(HttpServletRequest request, @RequestBody User user) {
        return new ResultResponse(userSrvice.query(user));
    }


    /**
     * 更新用户信息
     *
     * @param request
     * @param user
     * @return
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResultResponse submit(HttpServletRequest request, @RequestBody(required=false) User user) {
        return new ResultResponse(userSrvice.query(user));
    }


    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public ResultResponse getUser(@PathVariable String username) {
        User user = new User();
        user.setUsername(username);
        return new ResultResponse(userSrvice.query(user));
    }
}
