package com.hand.sxy.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不加 @Configuration 注解不生效
 *
 * @author spilledyear
 * @date 2018/4/21 18:42
 */
@Configuration
@WebFilter(urlPatterns = "/*")
public class CorsFilter implements Filter {
    private Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        logger.debug("跨域拦截");

        HttpServletResponse response = (HttpServletResponse) res;

        // 指定允许其他域名访问
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8082");

        response.setHeader("Access-Control-Allow-Credentials", "true");
        // 响应类型
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE");
        // 响应头设置
        response.setHeader("Access-Control-Allow-Headers", "token,Content-Type,Access-Control-Allow-Origin,Access-Control-Allow-Methods,Access-Control-Max-Age,authorization");
        response.setHeader("Access-Control-Max-Age", "3600");

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}

