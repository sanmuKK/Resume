package com.sanmukk.resume.api.admin.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface UserTableMapper extends BaseMapper<UserTable> {

    @Select("SELECT * FROM userTable")
    IPage<UserTable> selectPageVo(Page<?> page);
}
