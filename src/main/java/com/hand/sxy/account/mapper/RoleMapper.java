package com.hand.sxy.account.mapper;

import com.hand.sxy.account.dto.Role;
import com.hand.sxy.account.dto.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author spilledyear
 * @date 2018/4/21 13:33
 */
@Component
public interface RoleMapper {
    /**
     * 查询角色
     *
     * @param dto
     * @return
     */
    List<Role> query(Role dto);

    List<Role> queryByUser(User user);
}
