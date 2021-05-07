package com.sanmukk.resume.Interceptor;


import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.sanmukk.resume.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Component
@Slf4j
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse resp, Object handler)
            throws Exception {
        log.info("token验证");
        String token = request.getHeader("token");
        if (StringUtils.isNullOrEmpty(token) || !tokenUtil.verify(token)) {
            JSONObject ret = new JSONObject();
            ret.put("code", 1001);
            ret.put("message", "noPermission");
            PrintWriter pw = resp.getWriter();
            pw.write(ret.toJSONString());
            return false;
        } else {
            return true;
        }
    }
}
