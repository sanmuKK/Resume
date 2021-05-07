package com.sanmukk.resume.api.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanmukk.resume.api.admin.entity.Role;
import com.sanmukk.resume.api.admin.mapper.RoleMapper;
import com.sanmukk.resume.api.admin.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Resource
    RoleMapper roleMapper;

    @Override
    public List<Role> selectAllRole(int adminId) {
        return roleMapper.selectAllRole(adminId);
    }
}
