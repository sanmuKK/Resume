package com.sanmukk.resume.api.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.sanmukk.resume.api.admin.mapper.AdminMapper;
import com.sanmukk.resume.api.admin.service.IAdminService;
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
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Resource
    AdminMapper adminMapper;

    @Override
    public IPage<Admin> selectAdminPage(Page<Admin> page) {
        return adminMapper.selectPageVo(page);
    }
}
