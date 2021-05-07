package com.sanmukk.resume.api.admin.controller.commonAdmin.userTable;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.service.impl.AdminRoleServiceImpl;
import com.sanmukk.resume.utils.TokenUtil;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.admin.service.impl.UserTableServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/admin/userTable")
@Slf4j
public class UserTableController {

    final UserTableServiceImpl userTableService;
    final AdminRoleServiceImpl adminRoleService;
    final TokenUtil tokenUtil;

    @Autowired
    public UserTableController(UserTableServiceImpl userTableService,
                               TokenUtil tokenUtil,
                               AdminRoleServiceImpl adminRoleService) {
        this.userTableService = userTableService;
        this.tokenUtil = tokenUtil;
        this.adminRoleService = adminRoleService;
    }

    @GetMapping("/getUserTable")
    public UserTable getUserTable(@RequestParam int id) {
        log.info("通过ID获取UserTable");
        return userTableService.getById(id);
    }

    @GetMapping("/getUserTableList")
    public JSONObject getUserTableList(@RequestParam int page) {
        log.info("获取所有UserTable");
        Page<UserTable> userTablePage = new Page<>(page, 6);
        IPage<UserTable> iPage = userTableService.selectUserTablePage(userTablePage);
        JSONObject ret = new JSONObject();
        ret.put("UserTableList", iPage.getRecords());
        ret.put("total", iPage.getTotal());
        return ret;
    }

    @PostMapping("/uploadUserTable")
    public JSONObject uploadUserTable(@RequestBody UserTable usertable
            , @RequestHeader("token") String token) {
        log.info("上传UserTable");
        JSONObject ret = new JSONObject();
        if (adminRoleService.isRole(token, "commonAdminIssue")) {
            JSONObject tokenJson = tokenUtil.getTokenContext(token);
            usertable.setOwnerAdminId(tokenJson.getInteger("id"));
            userTableService.save(usertable);
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            ret.put("code", 1020);
            ret.put("message", "no enough permission");
        }
        return ret;
    }

    @DeleteMapping("/deleteUserTable")
    public JSONObject deleteUserTable(@RequestParam int userTableId
            , @RequestHeader("token") String token) {
        log.info("删除UserTable");
        JSONObject ret = new JSONObject();
        if (adminRoleService.isRole(token, "commonAdminIssue")) {
            JSONObject tokenJson = tokenUtil.getTokenContext(token);
            QueryWrapper<UserTable> wrapper = Wrappers.query();
            Map<String, Object> map = new HashMap<>();
            map.put("id", userTableId);
            map.put("ownerAdminId", tokenJson.getInteger("id"));
            wrapper.allEq(map);
            userTableService.remove(wrapper);
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            ret.put("code", 1020);
            ret.put("message", "no enough permission");
        }
        return ret;
    }
}
