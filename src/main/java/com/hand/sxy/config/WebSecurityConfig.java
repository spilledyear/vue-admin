package com.hand.sxy.config;

import com.hand.sxy.jwt.JwtAuthenticationEntryPoint;
import com.hand.sxy.jwt.JwtAuthorizationTokenFilter;
import com.hand.sxy.jwt.JwtTokenUtil;
import com.hand.sxy.security.CustomUserService;
import com.hand.sxy.security.MyFilterSecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

/**
 * @author spilledyear
 * @date 2018/4/24 13:19
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.route.authentication.path}")
    private String authenticationPath;

    @Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;


    /**
     * 通过这种方式注入 authenticationManagerBean ，然后在别的地方也可以用
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * //注册 UserDetailsService 的 Bean
     *
     * @return
     */
    @Bean
    UserDetailsService customUserService() {
        return new CustomUserService();
    }

    @Bean
    RememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.rememberMe().rememberMeServices(rememberMeServices());
//        httpSecurity.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);

        httpSecurity
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)

                /** 不创建 session **/
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/login", "/auth", "/oauth/*").permitAll()
                .antMatchers("/*.html", "/**/*.html", "/**/*.js", "/**/*.css").permitAll()
                .antMatchers("/api/role/query").hasRole("ADMIN")
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/api/system/login").usernameParameter("username").passwordParameter("password").permitAll()

                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/api/system/logout").permitAll();


        /**
         * spring security过滤器链中，真正的用户信息校验是 UsernamePasswordAuthenticationFilter 过滤器，然后才是权限校验。
         * 这里在 UsernamePasswordAuthenticationFilter过滤器之前 自定义一个过滤器，这样就可以提前根据token将authenticate信息
         * 维护进speing security上下文，然后在 UsernamePasswordAuthenticationFilter 得到的就已经是通过校验的用户了。
         */
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(customUserService(), jwtTokenUtil, tokenHeader);
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        /**
         * disable page caching
         *
         * 下面这行代码巨玄乎，加了这个之后，前端应用就无法正常访问了(也就是说需要开发/api/**权限才能正常 访问)
         */
//        httpSecurity.headers().frameOptions().sameOrigin().cacheControl();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        /**
         * userDetailService验证
         */
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(rawPassword.toString());
            }
        });
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity
                .ignoring().antMatchers(HttpMethod.POST, "/login", "/auth")

                .and()
                .ignoring().antMatchers("/**/*.html", "/**/*.js", "/**/*.css");
    }
}
