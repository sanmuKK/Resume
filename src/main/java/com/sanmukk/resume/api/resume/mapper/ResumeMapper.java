package com.sanmukk.resume.api.resume.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.resume.entity.Resume;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */

public interface ResumeMapper extends BaseMapper<Resume> {
    @Select("SELECT * FROM resume WHERE ownerUserTableId = #{ownerUserTableId} AND " +
            "ownerAdminId = #{ownerAdminId}")
    IPage<Resume> selectPageVo(Page<?> page, Integer ownerUserTableId, Integer ownerAdminId);
}
