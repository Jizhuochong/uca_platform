package com.uca.meeting.controller;

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
import com.uca.meeting.pojos.UcaMrApply;
import com.uca.meeting.service.MeetingApplyService;
import com.uca.meeting.service.MeetingService;

@Controller
@RequestMapping("/mrapply")
public class MeetingApplyController extends BaseController<UcaMrApply>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private MeetingApplyService meetingApplyService;
	
	@Autowired
	private MeetingService meetingService;
	
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("mrLi", this.meetingService.findMeetingRoomForAvailable());
		modelAndView.setViewName("/uca/meeting/mrapply_index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) String dMrId, @RequestParam(required = false) String qDate) throws Exception{
		JSONObject object = new JSONObject();
		this.meetingApplyService.findMrApply(object, Integer.valueOf(dMrId), qDate);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value="/getObjById", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public JSONObject getMrApply(@RequestParam(required = false) Integer applyId){
		JSONObject object = new JSONObject();
		this.meetingApplyService.getMrApply(object, applyId);
		return object;
	}
	
	@RequestMapping(value="/save", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public JSONObject saveMrApply(@RequestParam(required = false) Integer mrId, 
    		@RequestParam(required = false) Integer flag, 
            @RequestParam(required = false) String vleStr,
            @RequestParam(required = false) String conferenceName,
            @RequestParam(required = false) String participants,
            @RequestParam(required = false) String remark,
            @RequestParam(required = false) String applyDate,
            @RequestParam(required = false) String devices) throws Exception{
		JSONObject object = new JSONObject();
		UcaMrApply po = new UcaMrApply();
		po.setMrId(mrId);
		po.setFlag(flag);
		po.setVleStr(vleStr);
		po.setConferenceName(conferenceName);
		po.setParticipants(participants);
		po.setRemark(remark);
		po.setApplyDate(applyDate);
		po.setDevices(devices);
		this.meetingApplyService.saveOrRemoveMrApply(object, po);
        return object;
	}
	
	@RequestMapping(value = "/getMeetNoRead", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getMeetNoRead() {
        JSONObject object = new JSONObject();
        this.meetingApplyService.getMeetNoRead(object);
        return object;
    }

    @RequestMapping(value = "/meetRead", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject meetRead(@RequestParam(required = true) Integer applyUserId,
            @RequestParam(required = true) Integer applyId) {
        JSONObject object = new JSONObject();
        this.meetingApplyService.updateMeetRead(object, applyUserId, applyId);
        return object;
    }

}
