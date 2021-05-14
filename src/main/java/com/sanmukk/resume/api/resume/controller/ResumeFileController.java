package com.sanmukk.resume.api.resume.controller;


import com.alibaba.fastjson.JSONObject;
import com.sanmukk.resume.api.resume.entity.ResumeFile;
import com.sanmukk.resume.api.resume.entity.ResumeResumeFile;
import com.sanmukk.resume.api.resume.service.impl.ResumeResumeFileServiceImpl;
import com.sanmukk.resume.api.resume.service.impl.ResumeFileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author sanmuKK
 * @since 2021-04-20
 */
@RestController
@RequestMapping("/resume")
@Slf4j
public class ResumeFileController {

    final ResumeFileServiceImpl resumeFileService;
    final ResumeResumeFileServiceImpl resumeResumeFileService;
    ResumeFile resumeFile;
    ResumeResumeFile resumeResumefile;

    @Value("${file.uploadFolder.url}")
    private String uploadFilePath;

    @Autowired
    public ResumeFileController(ResumeFileServiceImpl resumeFileService
            , ResumeResumeFileServiceImpl resumeResumeFileService
            , ResumeFile resumeFile, ResumeResumeFile resumeResumefile) {
        this.resumeFileService = resumeFileService;
        this.resumeResumeFileService = resumeResumeFileService;
        this.resumeFile = resumeFile;
        this.resumeResumefile = resumeResumefile;
    }

    @PostMapping("/uploadFile")
    public JSONObject upload(@RequestParam("files") MultipartFile[] files
            , @RequestParam("resumeId") int resumeId) {
        JSONObject ret = new JSONObject();
        for (MultipartFile file : files) {
            String fileName = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            assert fileName != null;
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String savePath = uuid + fileType;
            String path = uploadFilePath + File.separator + savePath;
            File dest = new File(path);
            if (!dest.getParentFile().exists()) {
                if (!dest.getParentFile().mkdirs()) {
                    log.warn("上传文件失败,无存储文件夹");
                    ret.put("code", 1006);
                    ret.put("message", "程序错误");
                    return ret;
                }
            }
            try {
                file.transferTo(dest);
            } catch (Exception e) {
                log.warn("上传文件失败");
                ret.put("code", 1007);
                ret.put("message", "程序错误，请重新上传");
                return ret;
            }
            String uid = UUID.randomUUID().toString();
            resumeFile.setFileAddress(savePath);
            resumeFile.setFilename(fileName);
            resumeFile.setId(uid);
            System.out.println(uid);
            resumeFileService.save(resumeFile);
            resumeResumefile.setResumeId(resumeId);
            resumeResumefile.setResumeFileId(uid);
            System.out.println(resumeFile);
            resumeResumeFileService.save(resumeResumefile);
        }
        log.info("上传文件成功");
        ret.put("code", 1000);
        ret.put("message", "文件上传成功");
        return ret;
    }

    @GetMapping("/downloadFile")
    public void download(@RequestParam("resumeFileId") String resumeFileId,
                         HttpServletResponse response) {
        ResumeFile resumeFile = resumeFileService.getById(resumeFileId);
        try (FileInputStream fileInputStream = new FileInputStream(uploadFilePath +
                File.separator + resumeFile.getFileAddress());
             ServletOutputStream servletOutputStream = response.getOutputStream()) {
            if (fileInputStream.available() == 0) {
                log.warn("文件不存在");
            }
            log.info("下载文件");
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" +
                    resumeFile.getFilename());
            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) > 0) {
                servletOutputStream.write(bytes, 0, len);
            }
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

}
