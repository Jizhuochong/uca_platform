package com.uca.duty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.duty.pojos.UcaDuty;
import com.uca.duty.service.DutyService;

@Controller
@RequestMapping("/duty_user")
public class DutyUserController extends BaseController<UcaDuty>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private DutyService dutyService;
	
	/**
	 * 值班表首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/uca/duty/duty_user_index");
		return modelAndView;
	}
	
	/**
	 * 值班表列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(required = false) String qDate) throws Exception {
		// 分页查询
		JSONObject object = new JSONObject();
		this.dutyService.findDutyForUser(object, qDate);
		return JSON.toJSONStringWithDateFormat(object, "MM-dd",serializerFeatureNull);// 返回json格式的列表内容
	}
}
