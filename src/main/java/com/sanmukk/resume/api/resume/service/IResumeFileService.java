package com.sanmukk.resume.api.resume.service;

import com.sanmukk.resume.api.resume.entity.ResumeFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-20
 */
public interface IResumeFileService extends IService<ResumeFile> {
    List<ResumeFile> selectAllResumeFile(int resumeId);
}
