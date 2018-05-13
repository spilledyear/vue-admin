package com.hand.sxy.account.service.impl;

import com.hand.sxy.account.dto.Role;
import com.hand.sxy.account.mapper.RoleMapper;
import com.hand.sxy.account.service.IRoleSrvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:32
 */
@Service
public class RoleServiceImpl implements IRoleSrvice {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Role> query(Role dto) {
        return roleMapper.query(dto);
    }
}
