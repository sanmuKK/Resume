package com.sanmukk.resume.api.resume.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.admin.service.impl.UserTableServiceImpl;
import com.sanmukk.resume.utils.AccessLimitUtil;
import com.sanmukk.resume.api.resume.entity.Resume;
import com.sanmukk.resume.api.resume.service.impl.ResumeServiceImpl;
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
@RequestMapping("/resume")
@Slf4j
public class ResumeController {

    final ResumeServiceImpl resumeService;
    final UserTableServiceImpl userTableService;

    @Autowired
    public ResumeController(ResumeServiceImpl resumeService
            , UserTableServiceImpl userTableService) {
        this.resumeService = resumeService;
        this.userTableService = userTableService;
    }

    @AccessLimitUtil(times = 5, second = 30)
    @PostMapping("/uploadResume")
    public JSONObject uploadResume(@RequestBody Resume resume) {
        QueryWrapper<Resume> wrapper = Wrappers.query();
        Map<String, Object> map = new HashMap<>();
        map.put("stuNumber", resume.getStuNumber());
        map.put("ownerUserTableId", resume.getOwnerUserTableId());
        wrapper.allEq(map);
        Resume result = resumeService.getOne(wrapper, true);
        int resume_id;
        JSONObject ret = new JSONObject();
        if (result != null) {
            log.info("更新简历表单");
            UpdateWrapper<Resume> updateWrapper = Wrappers.update();
            updateWrapper.allEq(map);
            resumeService.update(resume, updateWrapper);
            resume_id = result.getId();
        } else {
            log.info("上传简历表单");
            resumeService.save(resume);
            resume_id = resume.getId();
        }
        log.info("简历表格字段更新");
        UserTable userTable = userTableService.getById(resume.getOwnerUserTableId());
        userTable.setHasNew(true);
        userTableService.updateById(userTable);
        ret.put("resumeId", resume_id);
        ret.put("code", 1000);
        ret.put("message", "success");
        return ret;
    }
}
