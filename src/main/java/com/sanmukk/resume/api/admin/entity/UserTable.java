package com.sanmukk.resume.api.admin.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Component
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "usertable", autoResultMap = true)
public class UserTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @TableField("introduce")
    private String introduce;

    @TableField(value = "content")
    private String content;

    @TableField("title")
    private String title;

    @TableField("hasNew")
    @JsonIgnore
    private Boolean hasNew;

    @TableField("ownerAdminId")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer ownerAdminId;
}
