package com.hand.sxy.account.mapper;

import com.hand.sxy.account.dto.User;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:33
 */
public interface UserMapper {
    /**
     * 查询用户
     *
     * @param user
     * @return
     */
    List<User> query(User user);
}
