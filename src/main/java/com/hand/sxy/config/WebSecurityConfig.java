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
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)

                // don't create session
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()


                .authorizeRequests()
                .antMatchers("/login", "/auth", "/oauth/*", "/api/**").permitAll()
                .antMatchers("/*.html", "/**/*.html", "/**/*.js", "/**/*.css").permitAll()
                .antMatchers("/api/role/query").hasRole("管理员")
                .anyRequest().authenticated()

                .and()
                .formLogin().loginPage("/login").loginProcessingUrl("/api/system/login").usernameParameter("username").passwordParameter("password").permitAll()

                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/api/system/logout").permitAll();

//        http.rememberMe().rememberMeServices(rememberMeServices());

//        http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class).csrf().disable();

        // Custom JWT based security filter
        JwtAuthorizationTokenFilter authenticationTokenFilter = new JwtAuthorizationTokenFilter(customUserService(), jwtTokenUtil, tokenHeader);
        http.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // disable page caching
        http.headers()
                .frameOptions().sameOrigin()  // required to set for H2 else H2 Console will be blank.
                .cacheControl();

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
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST,"/login", "/auth")

                .and()

                .ignoring().antMatchers("/**/*.html", "/**/*.js", "/**/*.css");
    }
}
