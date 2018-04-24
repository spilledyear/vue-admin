package com.hand.sxy.system.service.impl;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.service.IUserSrvice;
import com.hand.sxy.system.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:02
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    private IUserSrvice userSrvice;

    @Override
    public List<User> login(User user) {
        return userSrvice.query(user);
    }
}
