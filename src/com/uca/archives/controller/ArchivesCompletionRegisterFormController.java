package com.uca.archives.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.pojos.ArchivesCompletionDetailedForm;
import com.uca.archives.pojos.ArchivesCompletionRegisterForm;
import com.uca.archives.service.ArchivesCompletionDetailedFormService;
import com.uca.archives.service.ArchivesCompletionRegisterFormService;

@Controller
@RequestMapping("/registerForm/*")
public class ArchivesCompletionRegisterFormController extends BaseController<ArchivesCompletionRegisterForm> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private ArchivesCompletionRegisterFormService archivesCompletionRegisterFormService;
    @Autowired
    private ArchivesCompletionDetailedFormService archivesCompletionDetailedFormService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/archives/archives_completion_register_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String type, @RequestParam(required = false) String sd,
            @RequestParam(required = false) String ed) throws Exception {
        Page<ArchivesCompletionRegisterForm> page = this.getPageRows();
        JSONObject object = new JSONObject();

        archivesCompletionRegisterFormService.find(object, page, type, sd, ed, true);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/toListAll", method = { RequestMethod.GET, RequestMethod.POST })
    public String toListAll() throws Exception {
        return "uca/archives/archives_completion_register_list_all";
    }

    @RequestMapping(value = "/listAll", method = { RequestMethod.POST })
    @ResponseBody
    public String listAll(@RequestParam(required = false) String type, @RequestParam(required = false) String sd,
            @RequestParam(required = false) String ed) throws Exception {
        Page<ArchivesCompletionRegisterForm> page = this.getPageRows();
        JSONObject object = new JSONObject();

        archivesCompletionRegisterFormService.find(object, page, type, sd, ed, false);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@ModelAttribute ArchivesCompletionRegisterForm po) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionRegisterFormService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@ModelAttribute ArchivesCompletionRegisterForm po) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionRegisterFormService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionRegisterFormService.getById(object, id);
        return object;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionRegisterFormService.delete(object, id);
        return object;
    }

    @RequestMapping(value = "/toPrinter", method = { RequestMethod.GET, RequestMethod.POST })
    public String toPrinter(@RequestParam(required = true) int id, Model model) throws Exception {
//    	@RequestParam(required = true) int registerType,
        ArchivesCompletionRegisterForm register = archivesCompletionRegisterFormService.getById(id);
        if (null != register) {
            model.addAttribute("register", register);
        }
        List<ArchivesCompletionDetailedForm> detailedList = archivesCompletionDetailedFormService.findByRegisterId(id);
        if (null != detailedList && detailedList.size() > 0) {
            model.addAttribute("detailedList", detailedList);
        }
        return "uca/archives/archives_completion_register_form";
    }
    
    @RequestMapping(value="/down", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
	public ResponseEntity<byte[]> downLoad(@RequestParam(required = true) int id, @RequestParam(required = true) int type) throws Exception {
		File tmpFile = null;
		String filenamePrefix = null;	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        
		try {
			
			
			// 获取文件信息
			 ArchivesCompletionRegisterForm register = archivesCompletionRegisterFormService.getById(id);
		     // 判断文件是否为空
			Assert.notNull(register,"未找到相应文件的登记信息");
			// 设置文件名前缀
			filenamePrefix = UUID.randomUUID().toString();
			// 设置文件名称
			String fileName = "["+URLEncoder.encode(filenamePrefix, "UTF-8")+"]_"+System.currentTimeMillis()+".docx";
			headers.setContentDispositionFormData("attachment", fileName);
			
			if(type == 3 ){ //市政管线
				tmpFile = archivesCompletionRegisterFormService.getGeneratePhoneRecordExportFile(type, id);
		        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			} else if(type == 2){//市政公用
				tmpFile = archivesCompletionRegisterFormService.getGenerategyRecordExportFile(type, id);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			} else if(type == 1){//建筑工程
				tmpFile = archivesCompletionRegisterFormService.getGenerateJZRecordExportFile(type, id);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(tmpFile),headers, HttpStatus.OK);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
