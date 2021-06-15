package com.qfggk.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfggk.server.config.security.JwtTokenUtil;
import com.qfggk.server.mapper.AdminMapper;
import com.qfggk.server.pojo.Admin;
import com.qfggk.server.pojo.Menu;
import com.qfggk.server.pojo.RespBean;
import com.qfggk.server.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private  AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //获取验证码
        String captcha= (String) request.getSession().getAttribute("captcha");
        //校验验证码
        if(!StringUtils.hasLength(captcha)||!captcha.equalsIgnoreCase(code))
        {
            return RespBean.error("验证码输入错误，请重新输入");
        }
        //登录
        UserDetails userDetails=userDetailsService.loadUserByUsername(username);
        if(null==userDetails||!passwordEncoder.matches(password,userDetails.getPassword()))
        {
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled())
        {
            return RespBean.error("账户被禁用，请联系管理员");
        }

        //更新security登录用户对象
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap=new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功",tokenMap);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).
                eq("enabled",true));
    }



}
