package com.sanmukk.resume.api.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanmukk.resume.api.admin.entity.Role;
import com.sanmukk.resume.api.resume.entity.ResumeFile;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT role.roleName FROM admin_role " +
            "LEFT JOIN role ON admin_role.roleId=role.id " +
            "WHERE admin_role.adminId=#{adminId}")
    List<Role> selectAllRole(@Param("adminId") int adminId);
}
