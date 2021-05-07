package com.sanmukk.resume.api.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@TableName(value = "role", autoResultMap = true)
public class Role {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonIgnore
    private Integer id;

    @TableField(value = "roleName")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String roleName;

}
