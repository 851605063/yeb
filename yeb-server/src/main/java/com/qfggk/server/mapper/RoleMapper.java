package com.qfggk.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfggk.server.pojo.Role;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<Role> getRolesByAdminId(Integer adminId);

}
