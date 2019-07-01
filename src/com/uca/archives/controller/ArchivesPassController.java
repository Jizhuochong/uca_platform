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
@RequestMapping("/projectpass/*")
public class ArchivesPassController extends BaseController<UcaArchivesVo>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private ArchivesService archivesService;
	
	@RequestMapping(value = "/toList", method = {RequestMethod.GET, RequestMethod.POST })
	public String toList() throws Exception {
		return "uca/archives/archives_project_pass_list";
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list() throws Exception{
		Page<UcaArchivesVo> page = this.getPageRows();
		JSONObject object = new JSONObject();
		archivesService.findArchivesPass(object, page, UcaConstants.ARCHIVES_TYPE_PROJECT, 
				UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/renameArchives", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject renameArchives(@RequestParam(required = true) int archivesId, 
    		@RequestParam(required = true) String archivesNum) throws Exception{
        JSONObject object = new JSONObject();
        archivesService.renameArchives(object, archivesId, archivesNum);
        return object;
    }

}
