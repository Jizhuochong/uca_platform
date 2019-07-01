package com.uca.exam.controller;

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
import com.uca.exam.pojos.UcaAnswer;
import com.uca.exam.service.UcaAnswerService;

@Controller
@RequestMapping("/answer/*")
public class UcaAnswerController extends BaseController<UcaAnswer> {
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
	
    @Autowired
    private UcaAnswerService ucaAnswerService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/answer_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String epName, 
            @RequestParam(required = false) String userName) throws Exception {
        Page<UcaAnswer> page = this.getPageRows();
        JSONObject object = new JSONObject();
        ucaAnswerService.find(object, page, epName, userName);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int answerId) throws Exception{
        JSONObject object = new JSONObject();
        ucaAnswerService.getById(object, answerId);
        return object;
    }
    
    @RequestMapping(value = "/commitScore", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject commitScore(@RequestParam(required = true) int answerId, 
    		@RequestParam(required = false) String score) throws Exception{
        JSONObject object = new JSONObject();
        ucaAnswerService.commitScore(object, answerId, score);
        return object;
    }

}
