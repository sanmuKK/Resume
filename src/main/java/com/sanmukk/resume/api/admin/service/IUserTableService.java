package com.sanmukk.resume.api.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface IUserTableService extends IService<UserTable> {
    IPage<UserTable> selectUserTablePage(Page<UserTable> page);
}
