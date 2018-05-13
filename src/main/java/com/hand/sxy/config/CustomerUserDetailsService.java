//package com.hand.sxy.config;
//
//import com.hand.sxy.account.dto.User;
//import com.hand.sxy.account.mapper.UserMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.util.CollectionUtils;
//import org.springframework.util.StringUtils;
//
//import java.util.HashSet;
//import java.util.Set;
//
//public class CustomerUserDetailsService implements UserDetailsService {
//    private Logger logger = LoggerFactory.getLogger(CustomerUserDetailsService.class);
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (StringUtils.isEmpty(username)) {
//            logger.error("username is empty");
//            throw new UsernameNotFoundException("username is empty");
//        }
//
//        User user = userMapper.findByUserName(username);
//        if (null == user) {
//            logger.error("get user is null, userName:{}", username);
//            // return empty user
//            return new User(username, "", new HashSet<GrantedAuthority>());
//        }
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        if (!CollectionUtils.isEmpty(user.getRoleList())) {
////            for (Role role : user.getRoleList()) {
////                GrantedAuthority auth = new SimpleGrantedAuthority(role.getName());
////                authorities.add(auth);
////            }
//        }
//        // return user bean
////        return new User(user.getUsername(), user.getPassword(), authorities);
//    }
//}
