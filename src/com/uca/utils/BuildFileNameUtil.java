package com.uca.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.SystemProperties;

public class BuildFileNameUtil {
	public static final String UPLOAD_HOME = SystemProperties.getProperty("upload.home");
	public static final String UPLOAD_URL = SystemProperties.getProperty("upload.url");
	/**
	 * 获取文件名称
	 * @param fileName
	 * @return
	 */
	public static String getNewFileUrl(String fileName){
		 // 获取当前的年月作为文件夹名,按月存放文件
        String curr_month = DateUtil.getCurrentYearMonth();
        
        String context_real_root_folder_path = UPLOAD_HOME + UPLOAD_URL  + File.separator + curr_month  + File.separator;
        
        //创建文件夹,如果该文件夹还不存在
        File dirFile = new File(context_real_root_folder_path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        
        String file_name = getNewName(fileName);
        
        return context_real_root_folder_path + file_name;
	}
	
	/**
	 * 获取文件名称
	 * @param fileName
	 * @return
	 */
	public static String[] getNewFileUrlToArr(String fileName){
		 // 获取当前的年月作为文件夹名,按月存放文件
        String curr_month = DateUtil.getCurrentYearMonth();
        String real_path = curr_month  + File.separator;
        
        String context_real_root_folder_path = UPLOAD_HOME + UPLOAD_URL  + File.separator + real_path;
        
        //创建文件夹,如果该文件夹还不存在
        File dirFile = new File(context_real_root_folder_path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        
        String file_name = getNewName(fileName);
        
        return new String[]{context_real_root_folder_path + file_name, real_path + file_name};
	}
	
	/**
     * 获取文件后缀
     * @param fileName
     * @return
     */
	public static String getFileExt(String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return "";
        }
        int pos = fileName.lastIndexOf(".");
        if (pos == -1) {
            return "";
        } else {
            return fileName.substring(pos + 1, fileName.length());
        }
    }
    
	/**
     * 获取新文件名，按照日期加随机数规则
     * @param fileName
     * @return
     */
    public static final String getNewName(String fileName){
        Random random = new Random();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + random.nextInt(100) + "." + getFileExt(fileName);
    }
    
    /**
     * 获取新文件名，按照时间规则
     * @param fileName
     * @return
     */
    public static final String getTimeNewName(String fileName){
        String newName = String.valueOf(System.currentTimeMillis())+ "." +getFileExt(fileName);
        return newName;
    }
}
