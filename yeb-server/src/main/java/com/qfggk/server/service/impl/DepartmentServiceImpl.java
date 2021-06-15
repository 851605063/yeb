package com.qfggk.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfggk.server.mapper.DepartmentMapper;
import com.qfggk.server.pojo.Department;
import com.qfggk.server.service.IDepartmentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangjiaqi
 * @since 2021-06-13
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

}
