package com.sanmukk.resume.api.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanmukk.resume.api.admin.entity.UserTable;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT * FROM admin")
    IPage<Admin> selectPageVo(Page<?> page);
}
