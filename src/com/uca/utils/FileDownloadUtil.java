package com.uca.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.capinfo.core.utils.SystemProperties;

public class FileDownloadUtil {

    protected static Logger logger = LoggerFactory.getLogger(FileDownloadUtil.class);
    private static Object synchronizeOnSession = new Object();
    public static final String DOWNLOAD_HOME = SystemProperties.getProperty("upload.home");
    public static final String DOWNLOAD_URL = SystemProperties.getProperty("upload.url");
    
    /**
     * 工具方法 用于下载文件
     * 
     * @param request
     * @param response
     * @return
     */
    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response) {
        synchronized (synchronizeOnSession) {
            String path = request.getParameter("p");
            String fileName = request.getParameter("fn");
            if (null == path || "".equals(path)) {
                return false;
            }
            String url = DOWNLOAD_HOME + DOWNLOAD_URL + File.separator + path;
            File file = new File(url);
            if (!file.exists()) {
                return false;
            }
            return downloadFile(request, response, file, fileName);
        }
    }
    
    /**
     * 工具方法 用于下载文件
     * 
     * @param request
     * @param response
     * @param home 跟路径
     * @return
     */
    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, String home) {
        synchronized (synchronizeOnSession) {
            String path = request.getParameter("path");
            if (null == path || "".equals(path)) {
                return false;
            }
            String url = home + path;
            File file = new File(url);
            if (!file.exists()) {
                return false;
            }
            return downloadFile(request, response, file, null);
        }
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
    public static boolean downloadFile(HttpServletRequest request, HttpServletResponse response, File file, String newFileName) {
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
                return false;
            } finally {
                streamOut.close();
                fis.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
