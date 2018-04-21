package com.hand.sxy.system.service;

import com.hand.sxy.account.dto.User;

/**
 * @author spilledyear
 * @date 2018/4/21 13:01
 */
public interface ILoginService {
    boolean login(User user);
}
