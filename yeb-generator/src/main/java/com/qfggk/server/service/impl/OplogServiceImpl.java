package com.qfggk.server.service.impl;

import com.qfggk.server.pojo.Oplog;
import com.qfggk.server.mapper.OplogMapper;
import com.qfggk.server.service.IOplogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OplogServiceImpl extends ServiceImpl<OplogMapper, Oplog> implements IOplogService {

}
