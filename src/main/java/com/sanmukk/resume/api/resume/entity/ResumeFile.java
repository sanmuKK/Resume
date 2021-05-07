package com.sanmukk.resume.api.resume.entity;

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
@TableName(value = "resumefile", autoResultMap = true)
public class ResumeFile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("fileName")
    private String filename;

    @TableField("fileAddress")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String fileAddress;


}
