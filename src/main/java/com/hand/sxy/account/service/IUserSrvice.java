package com.hand.sxy.account.service;

import com.hand.sxy.account.dto.User;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:31
 */
public interface IUserSrvice {
    /**
     * 用户查询
     *
     * @param dto
     * @return
     */
    List<User> query(User dto);

    /**
     * 用户注册
     *
     * @param user
     */
    void register(User user);
}
