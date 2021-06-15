package com.qfggk.server.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfggk.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**当未登录或token失效时访问接口，自定义返回结果
 * @author: WJQ
 * @date 2021-06-14 14:43
 */
@Component
public class RestAuthorizedEntryPoint implements AuthenticationEntryPoint {


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        RespBean respBean=RespBean.error("尚未登录");
        respBean.setCode(401L);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();

    }
}
