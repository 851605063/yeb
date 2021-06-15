package com.qfggk.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfggk.server.mapper.EmployeeMapper;
import com.qfggk.server.pojo.Employee;
import com.qfggk.server.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
