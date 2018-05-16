package com.hand.sxy.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author spilledyear
 */
public class JwtAuthorizationTokenFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;
    private String tokenHeader;

    public JwtAuthorizationTokenFilter(UserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil, String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.tokenHeader = tokenHeader;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.debug("processing authentication for '{}'", request.getRequestURL());

        final String token = request.getHeader(this.tokenHeader);

        String username = null;
        if (token != null) {
            try {
                username = jwtTokenUtil.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                logger.error("从Token中获取用户名失败", e);
            } catch (ExpiredJwtException e) {
                logger.warn("这个Token已经失效了", e);
            }
        } else {
            logger.warn("请求头中未发现 Token, 将执行Spring Security正常啊的验证流程");
        }

        logger.debug("检查用户  '{}'  的权限", username);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            logger.debug("security context was null, so authorizating user");

            // 也可以将用户信息保存在token中，这时候就可以不用查数据库
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // 校验前端传过来的Token是否有问题
            if (jwtTokenUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("用户 '{}' 授权成功, 赋值给 SecurityContextHolder 上下文", username);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
}
