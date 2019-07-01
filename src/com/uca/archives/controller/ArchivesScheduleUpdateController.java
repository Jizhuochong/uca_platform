package com.uca.archives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;

@Controller
@RequestMapping("/scheduleUpdate/*")
public class ArchivesScheduleUpdateController extends BaseController<UcaArchivesVo>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private ArchivesService archivesService;
	
	@RequestMapping(value = "/toListProject", method = {RequestMethod.GET, RequestMethod.POST })
	public String toListProject() throws Exception {
		return "uca/archives/archives_project_schedule_list";
	}
	
	@RequestMapping(value = "/listProject", method = { RequestMethod.POST })
    @ResponseBody
    public String listProject() throws Exception{
		Page<UcaArchivesVo> page = this.getPageRows();
		JSONObject object = new JSONObject();
		archivesService.findArchivesSchedule(object, page, UcaConstants.ARCHIVES_TYPE_PROJECT, 
				UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/toListSound", method = {RequestMethod.GET, RequestMethod.POST })
	public String toListSound() throws Exception {
		return "uca/archives/archives_sound_schedule_list";
	}
	
	@RequestMapping(value = "/listSound", method = { RequestMethod.POST })
    @ResponseBody
    public String listSound() throws Exception{
		Page<UcaArchivesVo> page = this.getPageRows();
		JSONObject object = new JSONObject();
		archivesService.findArchivesSchedule(object, page, UcaConstants.ARCHIVES_TYPE_SOUND, 
				UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/copyFinish", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject copyFinish(@RequestParam(required = true) int archivesId) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.copyFinish(object, archivesId);
        return object;
    }
	
	@RequestMapping(value = "/editOrderNum", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject editOrderNum(@RequestParam(required = true) int archivesId, 
    		@RequestParam(required = true) String orderNum) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.editOrderNum(object, archivesId, orderNum);
        return object;
    }

}
