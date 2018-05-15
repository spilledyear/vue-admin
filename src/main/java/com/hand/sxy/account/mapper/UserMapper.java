package com.hand.sxy.account.mapper;

import com.hand.sxy.account.dto.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:33
 */
@Component
public interface UserMapper {
    /**
     * 查询用户
     *
     * @param dto
     * @return
     */
    List<User> query(User dto);


    /**
     * 根据用户名查找用户
     *
     * @param username
     * @return
     */
    User selectByUserName(String username);
}
