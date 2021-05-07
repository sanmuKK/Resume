package com.sanmukk.resume.api.admin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.sanmukk.resume.api.admin.entity.AdminRole;
import com.sanmukk.resume.api.admin.mapper.AdminRoleMapper;
import com.sanmukk.resume.api.admin.service.IAdminRoleService;
import com.sanmukk.resume.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements IAdminRoleService {
    @Resource
    TokenUtil tokenUtil;
    @Resource
    AdminServiceImpl adminService;
    @Resource
    RoleServiceImpl roleService;

    public boolean isRole(String token, String roleName) {
        JSONObject tokenJson = tokenUtil.getTokenContext(token);
        int id = tokenJson.getInteger("id");
        Admin superAdmin = adminService.getById(id);
        superAdmin.setRoles(roleService.selectAllRole(id));
        return superAdmin.getRole(roleName);
    }
}
