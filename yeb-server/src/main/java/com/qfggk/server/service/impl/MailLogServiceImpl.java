package com.qfggk.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qfggk.server.mapper.MailLogMapper;
import com.qfggk.server.pojo.MailLog;
import com.qfggk.server.service.IMailLogService;
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
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
