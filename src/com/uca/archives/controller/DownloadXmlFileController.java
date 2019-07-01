package com.uca.archives.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SystemProperties;

import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;

@Controller
@RequestMapping("/download")
public class DownloadXmlFileController {

    @Autowired
    private ArchivesService archivesService;
    
	/**
     * 下载
     * 
     * @param request
     * @param response
     * */
    @RequestMapping(value="/xmlFile", method={ RequestMethod.GET, RequestMethod.POST })
    public String downloadXmlFile(HttpServletRequest request, HttpServletResponse response) {
        String DOWNLOAD_HOME = SystemProperties.getProperty("upload.home");
        String DOWNLOAD_URL = SystemProperties.getProperty("upload.url");
        
        String id = request.getParameter("id");
        if (null == id || "".equals(id) || !ParameterUtil.isNumber(id)) {
            return null;
        }
        
        UcaArchives ucaArchives = archivesService.getArchivesById(Integer.parseInt(id));
        if(null == ucaArchives) {
            return null;
        }
        
        String path = ucaArchives.getFileUrl();
        String fileName = ucaArchives.getSourceFileName();
        if(ParameterUtil.isNull(path) || ParameterUtil.isNull(fileName)) {
            return null;
        }
        
        String url = DOWNLOAD_HOME + DOWNLOAD_URL + File.separator + path;
        File file = new File(url);
        if (file.exists()) {
            downloadFile(request, response, file, fileName);
        }
        return null;
    }
    
    /**
     * 下载
     * 
     * @param request
     * @param response
     * */
    @RequestMapping(value="/xmlFileByArchivesNum", method={ RequestMethod.GET, RequestMethod.POST })
    public String downloadXmlFileByArchivesNum(HttpServletRequest request, HttpServletResponse response) {
        String DOWNLOAD_HOME = SystemProperties.getProperty("upload.home");
        String DOWNLOAD_URL = SystemProperties.getProperty("upload.url");
        
        String id = request.getParameter("id");
        if (null == id || "".equals(id)) {
            return null;
        }
        
        UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(id);
        if(null == ucaArchives) {
            return null;
        }
        
        String path = ucaArchives.getFileUrl();
        String fileName = ucaArchives.getSourceFileName();
        if(ParameterUtil.isNull(path) || ParameterUtil.isNull(fileName)) {
            return null;
        }
        
        String url = DOWNLOAD_HOME + DOWNLOAD_URL + File.separator + path;
        File file = new File(url);
        if (file.exists()) {
            downloadFile(request, response, file, fileName);
        }
        return null;
    }
    
    /**
     * 工具方法 用于下载文件
     * 
     * @param request
     * @param response
     * @param file
     * @param newFileName
     * @return
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, File file, String newFileName) {
        String agent = request.getHeader("USER-AGENT");
        try {
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            ServletOutputStream streamOut = response.getOutputStream();
            try {
                response.reset();
                String contentTypeForView = request.getParameter("contentTypeForView");
                if (contentTypeForView != null && contentTypeForView.trim().length() > 0) {
                    response.setContentType(contentTypeForView.trim() + ";charset=UTF-8");
                } else {
                response.setContentType("application/x-msdownload;charset=UTF-8");
                if(null != newFileName) {
                    if (null != agent && -1 != agent.indexOf("MSIE")) {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=" + java.net.URLEncoder.encode(newFileName, "UTF-8").replace("+", "%20"));
                    } else {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=\"" + new String((newFileName).getBytes(), "iso-8859-1") + "\"");
                    }
                } else {
                    if (null != agent && -1 != agent.indexOf("MSIE")) {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=" + java.net.URLEncoder.encode(file.getName(), "UTF-8").replace("+", "%20"));
                    } else {
                        response.setHeader("Content-Disposition",
                                "attachment; filename=\"" + new String((file.getName()).getBytes(), "iso-8859-1") + "\"");
                    }
                }
                }
                int bytesRead = 0;
                byte[] buffer = new byte[4096];
                while ((bytesRead = fis.read(buffer, 0, 4096)) > 0) {
                    streamOut.write(buffer, 0, bytesRead);
                }
                streamOut.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                streamOut.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
