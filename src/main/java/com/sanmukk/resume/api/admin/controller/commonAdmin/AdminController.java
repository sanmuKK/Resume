package com.sanmukk.resume.api.admin.controller.commonAdmin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.admin.service.impl.AdminRoleServiceImpl;
import com.sanmukk.resume.api.admin.service.impl.UserTableServiceImpl;
import com.sanmukk.resume.api.resume.entity.Resume;
import com.sanmukk.resume.api.resume.service.impl.ResumeServiceImpl;
import com.sanmukk.resume.api.resume.service.impl.ResumeFileServiceImpl;
import com.sanmukk.resume.utils.SendEmailUtil;
import com.sanmukk.resume.utils.TokenUtil;
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
@RequestMapping("/admin/")
@Slf4j
public class AdminController {

    final UserTableServiceImpl userTableService;
    final ResumeServiceImpl resumeService;
    final ResumeFileServiceImpl resumeFileService;
    final AdminRoleServiceImpl adminRoleService;
    final SendEmailUtil sendEmailUtil;
    final TokenUtil tokenUtil;

    @Autowired
    public AdminController(UserTableServiceImpl userTableService
            , ResumeServiceImpl resumeService, SendEmailUtil sendEmailUtil
            , ResumeFileServiceImpl resumeFileService
            , AdminRoleServiceImpl adminRoleService
            , TokenUtil tokenUtil) {
        this.userTableService = userTableService;
        this.resumeService = resumeService;
        this.sendEmailUtil = sendEmailUtil;
        this.adminRoleService = adminRoleService;
        this.resumeFileService = resumeFileService;
        this.tokenUtil = tokenUtil;
    }

    @GetMapping("/confirmNewResume")
    public JSONObject confirmNewResume(@RequestParam int userTableId) {
        log.info("确认新简历");
        UserTable userTable = userTableService.getById(userTableId);
        userTable.setHasNew(false);
        userTableService.updateById(userTable);
        JSONObject ret = new JSONObject();
        ret.put("code", 1000);
        ret.put("message", "success");
        return ret;
    }

    @PostMapping("/passResume")
    public JSONObject passResume(@RequestParam int resumeId,
                                 @RequestHeader String token) {
        log.info("审核通过");
        JSONObject ret = new JSONObject();
        if (adminRoleService.isRole(token, "commonAdminPass")) {
            Resume resume = resumeService.getById(resumeId);
            resume.setIsPass(true);
            resumeService.updateById(resume);
            sendEmailUtil.sendEmail(resume.getEmail(), " 您的简历审核已通过");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            ret.put("code", 1002);
            ret.put("message", "no enough permission");
        }
        return ret;
    }

    @PostMapping("/notPassResume")
    public JSONObject notPassResume(@RequestParam int resumeId,
                                    @RequestHeader String token) {
        log.info("审核不通过");
        JSONObject ret = new JSONObject();
        if (adminRoleService.isRole(token, "commonAdminPass")) {
            Resume resume = resumeService.getById(resumeId);
            resume.setIsPass(false);
            resumeService.updateById(resume);
            sendEmailUtil.sendEmail(resume.getEmail(), " 您的简历审核未通过,请重新上传");
            ret.put("code", 1000);
            ret.put("message", "success");
        } else {
            ret.put("code", 1002);
            ret.put("message", "no enough permission");
        }
        return ret;
    }

    @GetMapping("/getResumeById")
    public Resume getResumeById(@RequestParam int resumeId,
                                @RequestHeader String token) {
        if (adminRoleService.isRole(token, "commonAdminRead")) {
            JSONObject tokenJson = tokenUtil.getTokenContext(token);
            Map<String, Object> map = new HashMap<>();
            map.put("id", resumeId);
            map.put("ownerAdminId", tokenJson.getInteger("id"));
            QueryWrapper<Resume> wrapper = Wrappers.query();
            wrapper.allEq(map);
            Resume resume = resumeService.getOne(wrapper);
            System.out.println(resume);
            System.out.println(resumeFileService.selectAllResumeFile(resume.getId()));
            resume.setResumeFileList(resumeFileService.selectAllResumeFile(resume.getId()));
            return resume;
        } else
            return null;
    }

    @GetMapping("/getResumeList")
    public JSONObject getResumeList(@RequestParam int ownerUserTableId,
                                      @RequestParam int page,
                                      @RequestHeader String token) {
        if (adminRoleService.isRole(token, "commonAdminRead")) {
            JSONObject tokenJson = tokenUtil.getTokenContext(token);
            Page<Resume> resumePage = new Page<>(page, 6);
            IPage<Resume> iPage = resumeService.selectResumePage(resumePage,
                    ownerUserTableId,tokenJson.getInteger("id"));
            JSONObject ret = new JSONObject();
            ret.put("total",iPage.getTotal());
            ret.put("ResumeList",iPage.getRecords());
            return ret;
        } else
            return null;
    }
}
