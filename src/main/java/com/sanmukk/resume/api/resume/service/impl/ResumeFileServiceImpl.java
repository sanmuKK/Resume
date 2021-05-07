package com.sanmukk.resume.api.resume.service.impl;

import com.sanmukk.resume.api.resume.entity.ResumeFile;
import com.sanmukk.resume.api.resume.mapper.ResumeFileMapper;
import com.sanmukk.resume.api.resume.service.IResumeFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-20
 */
@Service
public class ResumeFileServiceImpl extends ServiceImpl<ResumeFileMapper, ResumeFile> implements IResumeFileService {
    @Resource
    ResumeFileMapper resumefileMapper;

    @Override
    public List<ResumeFile> selectAllResumeFile(int resumeId) {
        return resumefileMapper.selectAllResumeFile(resumeId);
    }
}
