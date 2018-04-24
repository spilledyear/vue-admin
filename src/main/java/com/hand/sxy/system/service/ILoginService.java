package com.hand.sxy.system.service;

import com.hand.sxy.account.dto.User;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:01
 */
public interface ILoginService {
    /**
     * 登录
     *
     * @param user
     * @return
     */
    List<User> login(User user);
}
