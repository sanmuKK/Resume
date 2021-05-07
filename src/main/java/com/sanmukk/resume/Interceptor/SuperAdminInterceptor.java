package com.sanmukk.resume.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.sanmukk.resume.api.admin.service.impl.AdminRoleServiceImpl;
import com.sanmukk.resume.api.resume.service.impl.ResumeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
@Slf4j
public class SuperAdminInterceptor implements HandlerInterceptor {
    @Autowired
    private AdminRoleServiceImpl adminRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler)
            throws Exception {
        log.info("superAdmin验证");
        String token = request.getHeader("token");
        if (adminRoleService.isRole(token, "superAdmin")) {
            return true;
        }
        log.info("无超级管理员权限");
        JSONObject ret = new JSONObject();
        ret.put("code", 1012);
        ret.put("message", "no superAdmin Permission");
        PrintWriter pw = resp.getWriter();
        pw.write(ret.toJSONString());
        return false;
    }
}
