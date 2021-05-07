package com.sanmukk.resume.api.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.admin.mapper.UserTableMapper;
import com.sanmukk.resume.api.admin.service.IUserTableService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Service
public class UserTableServiceImpl extends ServiceImpl<UserTableMapper, UserTable> implements IUserTableService {

    @Resource
    UserTableMapper userTableMapper;

    @Override
    public IPage<UserTable> selectUserTablePage(Page<UserTable> page) {
        return userTableMapper.selectPageVo(page);
    }
}
