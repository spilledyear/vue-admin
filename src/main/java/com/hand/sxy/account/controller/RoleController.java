package com.hand.sxy.account.controller;

import com.hand.sxy.account.dto.Role;
import com.hand.sxy.account.service.IRoleSrvice;
import com.hand.sxy.system.dto.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author spilledyear
 * @date 2018/4/21 13:32
 */
@RestController
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleSrvice roleSrvice;

//    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/api/role/query", method = RequestMethod.POST)
    public ResultResponse query(HttpServletRequest request, @RequestBody Role dto) {
        return new ResultResponse(roleSrvice.query(dto));
    }
}
