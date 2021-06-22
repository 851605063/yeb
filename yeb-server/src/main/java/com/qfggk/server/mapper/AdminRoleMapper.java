package com.qfggk.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfggk.server.pojo.AdminRole;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
public interface AdminRoleMapper extends BaseMapper<AdminRole> {

    Integer addRole(Integer adminId, Integer[] rids);
}
