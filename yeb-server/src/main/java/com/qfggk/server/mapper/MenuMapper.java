package com.qfggk.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfggk.server.pojo.Admin;
import com.qfggk.server.pojo.Menu;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
public interface MenuMapper extends BaseMapper<Menu> {

     List<Menu> getMenusWithRole();

    List<Menu> getMenuByAdminId(Integer id);
}
