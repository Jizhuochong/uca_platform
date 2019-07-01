package com.uca.apply.controller;

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
import com.uca.apply.pojos.UcaApplyQueryRecord;
import com.uca.apply.service.ApplyRecordService;
import com.uca.constants.UcaConstants;

@Controller
@RequestMapping("/applyAuditing/*")
public class ApplyAuditingController extends BaseController<UcaApplyQueryRecord>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private ApplyRecordService applyRecordService;
	
	@RequestMapping(value = "/toList", method = {RequestMethod.GET, RequestMethod.POST })
	public String toList() throws Exception {
		return "uca/apply/apply_record_auditing_list";
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list() throws Exception{
		Page<UcaApplyQueryRecord> page = this.getPageRows();
		JSONObject object = new JSONObject();
		applyRecordService.findApplyRecordAuditing(object, page, UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/pass", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject pass(@RequestParam(required = true) int aqId, 
    		@RequestParam(required = true) String passTxt) throws Exception{
        JSONObject object = new JSONObject();
        applyRecordService.pass(object, aqId, passTxt);
        return object;
    }
	
	@RequestMapping(value = "/unpass", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject unpass(@RequestParam(required = true) int aqId, 
    		@RequestParam(required = true) String unPassTxt) throws Exception{
        JSONObject object = new JSONObject();
        applyRecordService.unpass(object, aqId, unPassTxt);
        return object;
    }

}
