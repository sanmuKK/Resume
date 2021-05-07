package com.sanmukk.resume.api.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanmukk.resume.api.admin.entity.Role;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface IRoleService extends IService<Role> {
    List<Role> selectAllRole(int adminId);
}
