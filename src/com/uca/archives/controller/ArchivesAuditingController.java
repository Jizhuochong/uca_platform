package com.uca.archives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SystemProperties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;

@Controller
@RequestMapping("/archivesAuditing/*")
public class ArchivesAuditingController extends BaseController<UcaArchivesVo>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private ArchivesService archivesService;
	
	@RequestMapping(value = "/toListProject", method = {RequestMethod.GET, RequestMethod.POST })
	public String toList(Model model) throws Exception {
	    String ServerPort = SystemProperties.getProperty("server.port");
	    model.addAttribute("ServerPort", ServerPort);
		return "uca/archives/archives_project_auditing_list";
	}
	
	@RequestMapping(value = "/listProject", method = { RequestMethod.POST })
    @ResponseBody
    public String listProject() throws Exception{
		Page<UcaArchivesVo> page = this.getPageRows();
		JSONObject object = new JSONObject();
		archivesService.findArchivesAuditing(object, page, UcaConstants.ARCHIVES_TYPE_PROJECT, 
				UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/toListSound", method = {RequestMethod.GET, RequestMethod.POST })
	public String toListSound() throws Exception {
		return "uca/archives/archives_sound_auditing_list";
	}
	
	@RequestMapping(value = "/listSound", method = { RequestMethod.POST })
    @ResponseBody
    public String listSound() throws Exception{
		Page<UcaArchivesVo> page = this.getPageRows();
		JSONObject object = new JSONObject();
		archivesService.findArchivesAuditing(object, page, UcaConstants.ARCHIVES_TYPE_SOUND, 
				UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/pass", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject pass(@RequestParam(required = true) int archivesId) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.pass(object, archivesId);
        return object;
    }
	
	@RequestMapping(value = "/unpass", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject unpass(@RequestParam(required = true) int archivesId, 
    		@RequestParam(required = true) String unPassTxt, 
    		@RequestParam(required = false) String fileUrl,
            @RequestParam(required = false) String sourceFileName) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.unpass(object, archivesId, unPassTxt, fileUrl, sourceFileName);
        return object;
    }
	
	@RequestMapping(value = "/auditing", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject auditing(@RequestParam(required = true) int archivesId, 
    		@RequestParam(required = true) String result) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.auditing(object, archivesId, result);
        return object;
    }

}
