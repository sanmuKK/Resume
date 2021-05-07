package com.sanmukk.resume.api.admin.controller.auth;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sanmukk.resume.utils.SHA256Util;
import com.sanmukk.resume.utils.TokenUtil;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.sanmukk.resume.api.admin.service.impl.AdminServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */

@RestController
@RequestMapping("/admin/auth")
@Slf4j
public class LoginController {

    final AdminServiceImpl adminService;
    final SHA256Util sha256Util;
    final TokenUtil tokenUtil;

    @Autowired
    public LoginController(AdminServiceImpl adminService,
                           SHA256Util sha256Util,
                           TokenUtil tokenUtil) {
        this.adminService = adminService;
        this.sha256Util = sha256Util;
        this.tokenUtil = tokenUtil;
    }

    @PostMapping("/login")
    public JSONObject adminLogin(@RequestBody Admin admin) {
        QueryWrapper<Admin> wrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("account", sha256Util.getSHA256(admin.getAccount()));
        map.put("password", sha256Util.getSHA256(admin.getPassword()));
        wrapper.allEq(map);
        Admin admin1 = adminService.getOne(wrapper);
        JSONObject ret = new JSONObject();
        if (admin1 != null) {
            log.info("管理员登录成功");
            ret.put("code", 1000);
            ret.put("message", "success");
            String token = tokenUtil.token(admin1.getId(), admin1.getName());
            ret.put("token", token);
        } else {
            log.info("管理员登录失败");
            ret.put("code", 1002);
            ret.put("message", "admin login fail");
        }
        return ret;
    }
}
