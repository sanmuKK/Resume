package com.sanmukk.resume.api.admin.entity;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sanmukk.resume.handle.AdminTypeHandler;
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
@TableName(value = "admin", autoResultMap = true)
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @TableField(value = "account", typeHandler = AdminTypeHandler.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String account;

    @TableField(value = "password", typeHandler = AdminTypeHandler.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @TableField("name")
    private String name;

    @TableField(exist = false)
    private List<Role> roles;


    public Boolean getRole(String roleName) {
        for (Role role : roles) {
            if (role.getRoleName().equals(roleName))
                return true;
        }
        return false;
    }
}
