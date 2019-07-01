package com.uca.meeting.controller;

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
import com.uca.meeting.service.MeetingApplyService;
import com.uca.meeting.service.MeetingService;
import com.uca.meeting.vo.UcaMrApplyStatisticsVo;

@Controller
@RequestMapping("/mrapplystic")
public class MrApplyStatisticsController extends BaseController<UcaMrApplyStatisticsVo> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private MeetingApplyService meetingApplyService;

    @Autowired
    private MeetingService meetingService;

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("mrLi", this.meetingService.findMeetingRoomForAvailable());
        modelAndView.setViewName("/uca/meeting/mrapplystic_index");
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) Integer type, @RequestParam(required = false) Integer mrId,
            @RequestParam(required = false) String bgTime, @RequestParam(required = false) String endTime)
            throws Exception {
        Page<UcaMrApplyStatisticsVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        this.meetingApplyService.findMrApplyForStatistics(object, page, type, mrId, bgTime, endTime);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/statisticsMeetUserIndex", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView statisticsMeetUserIndex() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/uca/meeting/statistics_meet_user_index");
        return modelAndView;
    }

    @RequestMapping(value = "/statisticsMeetUser", method = { RequestMethod.POST })
    @ResponseBody
    public String statisticsMeetUser(@RequestParam(required = false) String bgTime,
            @RequestParam(required = false) String endTime) throws Exception {
        JSONObject object = new JSONObject();
        this.meetingApplyService.findStatisticsMeetUser(object, bgTime, endTime);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

}
