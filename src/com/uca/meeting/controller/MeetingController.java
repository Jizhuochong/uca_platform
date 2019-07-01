package com.uca.meeting.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.meeting.pojos.UcaMeetingRoom;
import com.uca.meeting.service.MeetingService;

@Controller
@RequestMapping("/meeting")
public class MeetingController extends BaseController<UcaMeetingRoom>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/uca/meeting/meeting_index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String dName,
    		@RequestParam(required = false) String dStatus) throws Exception{
		Page<UcaMeetingRoom> page = this.getPageRows();
		JSONObject object = new JSONObject();
		this.meetingService.findMeetingRoom(object, page, dName, StringUtils.isNotEmpty(dStatus)?Integer.valueOf(dStatus):null);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/getObjById", method = { RequestMethod.POST })	
	@ResponseBody
	public JSONObject getObjById(@RequestParam(required = false) Integer id) throws Exception {
		JSONObject object = new JSONObject();
        this.meetingService.getMeetingRoomById(object, id);
        return object;
	}
	
	@RequestMapping(value="/save", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public JSONObject saveMeeting(@RequestParam(required = false) String mrId, 
    		@RequestParam(required = false) String name, 
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer peopleCount,
            @RequestParam(required = false) Integer status) throws Exception{
		JSONObject object = new JSONObject();
		UcaMeetingRoom po = new UcaMeetingRoom();
		po.setMrId(StringUtils.isNotEmpty(mrId)?Integer.valueOf(mrId):null);
		po.setName(name);
		po.setAddress(address);
		po.setPeopleCount(peopleCount);
		po.setStatus(status);
        this.meetingService.saveOrUpdateMeetingRoom(object, po);
        return object;
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int dId) throws Exception{
        JSONObject object = new JSONObject();
        meetingService.deleteMeetingRoomById(object, dId);
        return object;
    }
}
