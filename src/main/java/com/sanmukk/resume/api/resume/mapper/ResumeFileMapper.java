package com.sanmukk.resume.api.resume.mapper;

import com.sanmukk.resume.api.resume.entity.ResumeFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-20
 */
public interface ResumeFileMapper extends BaseMapper<ResumeFile> {

    @Select("SELECT resumefile.id,resumefile.fileName FROM resume_resumefile " +
            "LEFT JOIN resumefile ON resume_resumefile.resumeFileId=resumefile.id " +
            "WHERE resume_resumefile.resumeId=#{resumeId}")
    List<ResumeFile> selectAllResumeFile(@Param("resumeId") int resumeId);
}
