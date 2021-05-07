package com.sanmukk.resume.Interceptor;


import com.alibaba.fastjson.JSONObject;
import com.sanmukk.resume.utils.AccessLimitUtil;
import com.sanmukk.resume.utils.IpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    IpUtil ipUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(AccessLimitUtil.class)) {
                return true;
            }
            AccessLimitUtil accessLimit = method.getAnnotation(AccessLimitUtil.class);
            if (accessLimit == null) {
                return true;
            }
            int times = accessLimit.times();
            int second = accessLimit.second();
            String key = ipUtil.getIpAddr(request) + request.getRequestURI();
            Integer maxTimes = null;
            log.info("连接redis");
            String value = redisTemplate.opsForValue().get(key);
            if (StringUtils.isNotEmpty(value)) {
                maxTimes = Integer.valueOf(value);
            }
            if (maxTimes == null) {
                redisTemplate.opsForValue().set(key, "1", second, TimeUnit.SECONDS);
            } else if (maxTimes < times) {
                redisTemplate.opsForValue().set(key, maxTimes + 1 + "", second, TimeUnit.SECONDS);
            } else {
                log.info(key + " 请求过于频繁");
                PrintWriter pw = response.getWriter();
                JSONObject ret = new JSONObject();
                ret.put("code", 1007);
                ret.put("message", "Requests are too frequent");
                pw.write(ret.toJSONString());
                return false;
            }
        }
        return true;
    }
}
