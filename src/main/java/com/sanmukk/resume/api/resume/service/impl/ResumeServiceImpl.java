package com.sanmukk.resume.api.resume.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sanmukk.resume.api.admin.entity.UserTable;
import com.sanmukk.resume.api.admin.mapper.UserTableMapper;
import com.sanmukk.resume.api.resume.entity.Resume;
import com.sanmukk.resume.api.resume.mapper.ResumeMapper;
import com.sanmukk.resume.api.resume.service.IResumeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-07
 */
@Service
public class ResumeServiceImpl extends ServiceImpl<ResumeMapper, Resume> implements IResumeService {
    @Resource
    ResumeMapper resumeMapper;

    @Override
    public IPage<Resume> selectResumePage(Page<Resume> page, Integer ownerUserTableId,
                                          Integer ownerAdminId) {
        return resumeMapper.selectPageVo(page, ownerUserTableId, ownerAdminId);
    }

}
