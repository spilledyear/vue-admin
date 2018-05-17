package com.hand.sxy.account.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.service.IUserSrvice;
import com.hand.sxy.system.dto.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author spilledyear
 * @date 2018/4/21 13:32
 */
@RestController
@CrossOrigin
public class UserController {
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserSrvice userSrvice;

    @RequestMapping(value = "/api/user/query")
    public ResultResponse query(HttpServletRequest request, @RequestBody User user) {
        return new ResultResponse(userSrvice.query(user));
    }
}
