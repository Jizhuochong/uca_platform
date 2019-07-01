package com.uca.archives.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.uca.utils.BuildFileNameUtil;

@Controller
@RequestMapping("/upload_file")
public class UploadFileController {
	@RequestMapping(value = "/upload")
//	@ResponseBody
	public void upload(HttpServletResponse response, HttpSession session, @RequestParam("file") MultipartFile file) throws IOException{
		String[] fileUrl = null;
//		String path = request.getContextPath();
		response.setContentType("text/plain;charset=utf-8");
		String fileName = "";
		JSONObject object = new JSONObject();
		if (!file.isEmpty()) {
			fileName = file.getOriginalFilename();//原始文件名称
			try {
//				String tomcatPath = request.getServletContext().getRealPath("/resources/upload/"); //得到保存的路径
//				fileUrl = tomcatPath +"/" +  fileName;
				fileUrl = BuildFileNameUtil.getNewFileUrlToArr(fileName);
				FileCopyUtils.copy(file.getBytes(), new File(fileUrl[0]));
				object.put("fileUrl", fileUrl[1]);
				object.put("fileName", fileName);
				object.put("fileFormat", fileName.substring(fileName.lastIndexOf(".")));
				object.put("fileSize", file.getSize());
				object.put("msg", "上传成功！");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				throw e;
			}
		}
//		"{\"fileUrl\":\"" + URLEncoder.encode(fileUrl[1], "UTF-8") + "\",\"fileName\":\"" + URLEncoder.encode(fileName, "UTF-8") + "\"}";
//		return object;
		response.getWriter().write(object.toString());
	}
	
	public static void main(String[] args){
		String fileUrl = "/sdfss/sdfsf/sfds";
		String[] fileArr = fileUrl.split(File.separator);
		for(String str : fileArr){
			System.out.println(str);
		}
	}
}