package com.hand.sxy.account.service.impl;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.mapper.UserMapper;
import com.hand.sxy.account.service.IUserSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:32
 */
@Service
public class UserServiceImpl implements IUserSrvice {

    @Autowired
    UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> query(User dto) {
        return userMapper.query(dto);
    }

    @Override
    public void register(User user) {
        String passwordEncrypted = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncrypted);

        userMapper.insert(user);
    }
}
