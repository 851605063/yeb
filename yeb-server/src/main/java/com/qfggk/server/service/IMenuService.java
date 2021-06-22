package com.qfggk.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qfggk.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
public interface IMenuService extends IService<Menu> {

    List<Menu> getMenuByAdminId();

    List<Menu> getMenusWithRole();
}
