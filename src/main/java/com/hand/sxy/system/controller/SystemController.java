package com.hand.sxy.system.controller;

import com.hand.sxy.account.dto.User;
import com.hand.sxy.jwt.AuthenticationException;
import com.hand.sxy.jwt.JwtAuthenticationResponse;
import com.hand.sxy.jwt.JwtTokenUtil;
import com.hand.sxy.jwt.JwtUser;
import com.hand.sxy.system.dto.Result;
import com.hand.sxy.system.service.ILoginService;
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

    @Autowired
    private ILoginService loginService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("customUserService")
    private UserDetailsService userDetailsService;


    @RequestMapping(value = "/api/system/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest request, User user) {

        List<User> userList = loginService.login(user);
        Result result = new Result(userList);

        if (userList == null || userList.isEmpty()) {
            logger.info("登录失败，用户名或密码错误");
            result.setSuccess(false);
            result.setMessage("用户名或密码错误");
            return result;
        }

        logger.info("登录成功");
        return result;
    }

    @RequestMapping("/login")
    public String require(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        /**
         * 返回的内容就是templetes下面文件的名称
         * return new ModelAndView("loginPage", map);
         */
        return "login";
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {

        request.getSession().setAttribute("test", "6666666666666");

        return "index";
    }


    /**
     * 获取token
     *
     * @param user
     * @return
     * @throws AuthenticationException
     */
    @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
    public ResponseEntity<?> auth(@RequestBody User user) throws AuthenticationException {

        authenticate(user.getUsername(), user.getPassword());

        // Reload password post-security so we can generate the token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Return the token
        return ResponseEntity.ok(new Result(token));
    }

    @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(tokenHeader);
        final String token = authToken.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    /**
     * Authenticates the user. If something is wrong, an {@link AuthenticationException} will be thrown
     */
    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
}
