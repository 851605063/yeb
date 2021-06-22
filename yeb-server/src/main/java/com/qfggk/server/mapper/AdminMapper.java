package com.qfggk.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qfggk.server.pojo.Admin;
import com.qfggk.server.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */

public interface AdminMapper extends BaseMapper<Admin> {

    /**
     * 获取所有操作员
     * @param id
     * @param keywords
     * @return
     */
    List<Admin> getAllAdmins(@Param("id") Integer id, @Param("keywords") String keywords);

}
