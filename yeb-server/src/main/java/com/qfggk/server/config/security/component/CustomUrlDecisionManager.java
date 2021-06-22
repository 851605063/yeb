package com.qfggk.server.config.security.component;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * 权限控制
 * 判断用户角色（请求过来的这个用户具有的角色是否在url允许的角色中）
 * @author: WJQ
 * @date 2021-06-16 20:24
 */
@Component
public class CustomUrlDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        //当前url所需角色
        for(ConfigAttribute configAttribute:configAttributes) {
            String needrole = configAttribute.getAttribute();
            System.out.println(needrole);
            //判断用户具有的角色是否登录即可访问的角色
            if(needrole.equals("ROLE_LOGIN"))
            {
                if(authentication instanceof AnonymousAuthenticationToken)
                {
                    throw new AccessDeniedException("尚未登录，请登录!");
                }
                else
                    return;
            }
            //判断用户角色是否为url允许的角色
            Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
            for(GrantedAuthority grantedAuthority:authorities)
            {
                if(grantedAuthority.getAuthority().equals(needrole))
                {
                    return;
                }
            }
        }
        throw  new AccessDeniedException("权限不足");

    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
