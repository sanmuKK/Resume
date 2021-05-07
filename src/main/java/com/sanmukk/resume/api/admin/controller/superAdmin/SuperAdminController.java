package com.sanmukk.resume.api.admin.controller.superAdmin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.sanmukk.resume.api.admin.entity.AdminRole;
import com.sanmukk.resume.api.admin.service.impl.AdminRoleServiceImpl;
import com.sanmukk.resume.api.admin.service.impl.AdminServiceImpl;
import com.sanmukk.resume.api.admin.service.impl.RoleServiceImpl;
import com.sanmukk.resume.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */


@RestController
@RequestMapping("/superAdmin")
@Slf4j
public class SuperAdminController {

    final AdminServiceImpl adminService;
    final RoleServiceImpl roleService;
    final AdminRoleServiceImpl adminRoleService;
    final TokenUtil tokenUtil;

    @Autowired
    public SuperAdminController(AdminServiceImpl adminService,
                                RoleServiceImpl roleService,
                                AdminRoleServiceImpl adminRoleService,
                                TokenUtil tokenUtil) {
        this.adminService = adminService;
        this.roleService = roleService;
        this.adminRoleService = adminRoleService;
        this.tokenUtil = tokenUtil;
    }

    @GetMapping("/getAdminById")
    public Admin getAdminById(@RequestParam int adminId) {
        log.info("获取Admin");
        if (adminId != 0) {
            Admin admin = adminService.getById(adminId);
            admin.setRoles(roleService.selectAllRole(adminId));
            return admin;
        }
        return null;
    }

    @GetMapping("/getAdminList")
    public JSONObject getAdminList(@RequestParam int page) {
        log.info("获取AdminList");
        Page<Admin> adminPage = new Page<>(page, 6);
        IPage<Admin> iPage = adminService.selectAdminPage(adminPage);
        List<Admin> adminList = iPage.getRecords();
        adminList.remove(0);
        for (Admin admin : adminList) {
            admin.setRoles(roleService.selectAllRole(admin.getId()));
        }
        JSONObject ret = new JSONObject();
        ret.put("AdminList", iPage.getRecords());
        ret.put("total", iPage.getTotal() - 1);
        return ret;
    }

    @PostMapping("/addAdmin")
    public JSONObject addAdmin(@RequestBody Admin admin) {
        JSONObject ret = new JSONObject();
        try {
            adminService.save(admin);
            log.info("添加小组管理员成功");
            ret.put("code", 1000);
            ret.put("message", "success");
        } catch (DuplicateKeyException e) {
            log.warn("添加小组管理员失败,用户名已存在");
            e.printStackTrace();
            ret.put("code", 1011);
            ret.put("message", "添加小组管理员失败，用户名已存在");
        }
        return ret;
    }

    @DeleteMapping("/delAdmin")
    public JSONObject delAdmin(@RequestParam int adminId) {
        QueryWrapper<Admin> wrapper = Wrappers.query();
        wrapper.eq("id", adminId);
        JSONObject ret = new JSONObject();
        if (adminService.remove(wrapper)) {
            log.info("管理员回收");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            log.info("无此管理员");
            ret.put("code", 1012);
            ret.put("message", "no admin");
        }
        return ret;
    }

    @PostMapping("/PermissionControl/addCommonAdminRead")
    public JSONObject PermissionControlRead(@RequestParam int adminId) {
        JSONObject ret = new JSONObject();
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(adminId);
        adminRole.setRoleId(4);
        if (adminRoleService.save(adminRole)) {
            log.info("设置管理员读取简历权限");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            log.warn("设置管理员读取简历权限失败");
            ret.put("code", 1017);
            ret.put("message", "Rbac Error");
        }
        return ret;
    }

    @PostMapping("/PermissionControl/addCommonAdminPass")
    public JSONObject PermissionControlPass(@RequestParam int adminId) {
        JSONObject ret = new JSONObject();
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(adminId);
        adminRole.setRoleId(3);
        if (adminRoleService.save(adminRole) &&
                adminRoleService.save(adminRole.getNewAdminRole(4))) {
            log.info("设置管理员审核简历权限成功");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            log.warn("设置管理员审核简历权限失败");
            ret.put("code", 1017);
            ret.put("message", "Rbac Error");
        }
        return ret;
    }

    @PostMapping("/PermissionControl/addCommonAdminIssue")
    public JSONObject PermissionControlIssue(@RequestParam int adminId) {
        JSONObject ret = new JSONObject();
        AdminRole adminRole = new AdminRole();
        adminRole.setAdminId(adminId);
        adminRole.setRoleId(2);
        if (adminRoleService.save(adminRole) &&
                adminRoleService.save(adminRole.getNewAdminRole(3)) &&
                adminRoleService.save(adminRole.getNewAdminRole(4))) {
            log.info("设置管理员审核简历权限成功");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            log.warn("设置管理员审核简历权限失败");
            ret.put("code", 1017);
            ret.put("message", "Rbac Error");
        }
        return ret;
    }
}
