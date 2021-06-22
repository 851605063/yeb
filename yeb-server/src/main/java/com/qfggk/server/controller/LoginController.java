package com.qfggk.server.controller;

import com.qfggk.server.pojo.Admin;
import com.qfggk.server.pojo.AdminLoginParam;
import com.qfggk.server.pojo.RespBean;
import com.qfggk.server.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * @author: WJQ
 * @date 2021-06-13 22:46
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {

    @Autowired
    private IAdminService iAdminService;

    @ApiOperation(value = "登录之后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request)
    {
        return iAdminService.login(adminLoginParam.getUsername(),adminLoginParam.getPassword(),adminLoginParam.getCode(),request);
    }

    /**
     * 前端调用logout方法后只要收到200的返回码就在请求头里删除token
     * @return
     */
    @ApiOperation("退出登录")
    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }

    /**
     * principal可以从springsecurity中获取当前登录对象
     * @param principal
     * @return
     */
    @ApiOperation(value = "获取登录用户信息")
    @GetMapping("/admin/info")
    public Admin getAdminInfo(Principal principal)
    {
        if(null==principal)
        {
            return null;
        }
        String username=principal.getName();
        Admin admin=iAdminService.getAdminByUserName(username);
        admin.setPassword(null);
        admin.setRoles(iAdminService.getRolesByAdminId(admin.getId()));
        return admin;
    }

}
