package com.sanmukk.resume.api.resume.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName(value = "resume", autoResultMap = true)
public class Resume implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @TableField("stuNumber")
    private String stuNumber;

    @TableField("email")
    private String email;

    @TableField(value = "content")
    private String content;

    @TableField(value = "isPass")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Boolean isPass;

    @TableField("uploadTime")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime uploadTime;

    @TableField("ownerAdminId")
    private Integer ownerAdminId;

    @TableField("ownerUserTableId")
    private Integer ownerUserTableId;

    @TableField(exist = false)
    private List<ResumeFile> resumeFileList;
}
