package com.qfggk.server.config.security;

import com.qfggk.server.config.security.component.JwtAuthencationTokenFilter;
import com.qfggk.server.config.security.component.RestAuthorizedEntryPoint;
import com.qfggk.server.config.security.component.RestfulAccessDeniedHandler;
import com.qfggk.server.pojo.Admin;
import com.qfggk.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author: WJQ
 * @date 2021-06-14 11:33
 */
@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired(required = false)
    private IAdminService adminService;
    @Autowired(required = false)
    private RestAuthorizedEntryPoint restAuthorizedEntryPoint;
    @Autowired(required = false)
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 放行指定路径，不过拦截链
     * @param web
     * @throws Exception
     */



    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/login",
                "/logout",
                "/ws/**",
                "/css/**",
                "/js/**",
                "/index.html",
                "favicon.ico",
                "/doc.html",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/api-docs/**",
                "/captcha",
                "/"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //使用JWT，不需要csrf
        http.csrf()
                .disable()
                //基于token，不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
//                //允许访问
//                .antMatchers("/login","logout")
//                .permitAll()
                //除了上面，所有请求都要认证
                .anyRequest()
                .authenticated()
                .and()
                //禁用缓存
                .headers()
                .cacheControl();
        //添加JWT过滤器，登录授权过滤器
        http.addFilterBefore(jwtAuthencationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        //添加自定义未授权和未登录结果返回
        http.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthorizedEntryPoint);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
       return username ->{
           Admin admin =adminService.getAdminByUserName(username);
           if(admin!=null) {
               admin.setRoles(adminService.getRolesByAdminId(admin.getId()));
               return admin;
           }
           throw new UsernameNotFoundException("用户名或密码不正确");
       };
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtAuthencationTokenFilter jwtAuthencationTokenFilter()
    {
        return new JwtAuthencationTokenFilter();
    }
}
