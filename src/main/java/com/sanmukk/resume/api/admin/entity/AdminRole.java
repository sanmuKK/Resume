package com.sanmukk.resume.api.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "admin_role", autoResultMap = true)
public class AdminRole {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @TableField(value = "roleId")
    private int roleId;

    @TableField(value = "adminId")
    private int adminId;

    public AdminRole getNewAdminRole(int roleId) {
        this.roleId = roleId;
        return this;
    }
}
