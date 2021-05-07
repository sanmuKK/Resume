package com.sanmukk.resume.api.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.sanmukk.resume.api.admin.entity.AdminRole;
import com.sanmukk.resume.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface IAdminRoleService extends IService<AdminRole> {
}
