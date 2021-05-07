package com.sanmukk.resume.api.resume.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@TableName(value = "resume_resumefile", autoResultMap = true)
public class ResumeResumeFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @TableField("resumeId")
    private Integer resumeId;

    @TableField("resumeFileId")
    private String resumeFileId;


}
