package com.hand.sxy.system.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.account.service.IUserSrvice;
import com.hand.sxy.jwt.AuthenticationException;
import com.hand.sxy.jwt.JwtTokenUtil;
import com.hand.sxy.security.CustomUser;
import com.hand.sxy.system.dto.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author spilledyear
 * @date 2018/4/21 12:58
 */
@Controller
public class SystemController {
    private Logger logger = LoggerFactory.getLogger(SystemController.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("customUserService")
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserSrvice userService;


    /**
     * 认证接口，用于前端获取 JWT 的接口
     *
     * @param user
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    @ResponseBody
    public ResultResponse obtainToken(@RequestBody User user) throws AuthenticationException {

        /**
         * 通过调用 spring security 中的 authenticationManager 对用户进行验证
         */
        Objects.requireNonNull(user.getUsername());
        Objects.requireNonNull(user.getPassword());
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        } catch (DisabledException e) {
            throw new AuthenticationException("该已被被禁用，请检查", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("无效的密码，请检查", e);
        }

        /**
         * 根据用户名从数据库获取用户信息，然后生成 token
         */
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        List<User> userList = userService.query(user);
        ResultResponse resultSet = new ResultResponse(true, token);
        resultSet.setRows(userList);
        return resultSet;
    }


    /**
     * 刷新Token
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResultResponse refreshToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        CustomUser customUser = (CustomUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, customUser.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return new ResultResponse(true, refreshedToken);
        } else {
            return new ResultResponse(false);
        }
    }

    /**
     * 处理 AuthenticationException 异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }


    @RequestMapping("/login")
    public String require(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        /**
         * 返回的内容就是templetes下面文件的名称
         * return new ModelAndView("loginPage", map);
         */
        return "login";
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public ResultResponse register(@RequestBody User user) {
        userService.register(user);
        return new ResultResponse();
    }


    /**
     * 首页
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

        request.getSession().setAttribute("test", "6666666666666");

        return "index";
    }
}
