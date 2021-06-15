package com.qfggk.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfggk.server.pojo.Admin;
import com.qfggk.server.pojo.Menu;
import com.qfggk.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登录之后返回token
     * @param username
     * @param passwd
     * @param code
     * @param request
     * @return
     */
    public RespBean login(String username, String passwd, String code, HttpServletRequest request);


    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);


}
