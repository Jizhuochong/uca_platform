package com.uca.archives.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.uca.utils.FileDownloadUtil;

@Controller
@RequestMapping("/download_file")
public class DownloadFileController {
	/**
     * 下载
     * 
     * @param request
     * @param response
     * */
    @RequestMapping(value="/dbp", method={ RequestMethod.GET, RequestMethod.POST })
    public String downloadByPath(HttpServletRequest request, HttpServletResponse response) {
        FileDownloadUtil.downloadFile(request, response);
        return null;
    }
}
