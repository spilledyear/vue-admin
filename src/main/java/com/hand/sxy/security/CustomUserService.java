package com.hand.sxy.security;

import com.hand.sxy.account.dto.Role;
import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.mapper.RoleMapper;
import com.hand.sxy.account.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * @author spilledyear
 * <p>
 * 自定义userdetailsservice 接口
 */
@Service
public class CustomUserService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(CustomUserService.class);

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            logger.error("username is empty");
            throw new UsernameNotFoundException("username is empty");
        }

        User user = userMapper.selectByUserName(username);
        if (null == user) {
            logger.error("get user is null, userName:{}", username);
            throw new UsernameNotFoundException("username is empty");
        }


        List<Role> roleList = roleMapper.queryByUser(user);
        Set<GrantedAuthority> authorities = new HashSet<>();
        if (!CollectionUtils.isEmpty(roleList)) {
            for (Role role : roleList) {
//                GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(), permission.getMethod());
                GrantedAuthority auth = new SimpleGrantedAuthority(role.getRoleName());
                authorities.add(auth);
            }
        }

        UserDetails userDetails = new CustomUser(user.getUserId(), user.getUsername(), user.getPassword(),
                true, true, true, true, authorities, null);
        return userDetails;
    }

}
