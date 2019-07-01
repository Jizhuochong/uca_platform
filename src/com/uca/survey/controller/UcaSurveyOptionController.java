package com.uca.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.survey.pojos.UcaSurveyOption;
import com.uca.survey.service.UcaSurveyOptionService;

@Controller
@RequestMapping("/surveyoption/*")
public class UcaSurveyOptionController extends BaseController<UcaSurveyOption> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UcaSurveyOptionService ucaSurveyOptionService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList(@RequestParam(required = true) int surveyId, Model model) throws Exception {
    	model.addAttribute("surveyId", surveyId);
        return "uca/survey/surveyoption_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) int surveyId, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaSurveyOption> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaSurveyOptionService.find(object, page, surveyId, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String surveyId, 
            @RequestParam(required = true) String options) throws Exception{
        JSONObject object = new JSONObject();
        UcaSurveyOption po = new UcaSurveyOption();
        po.setSurveyId(Integer.parseInt(surveyId));
        po.setOptions(options);
        ucaSurveyOptionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String surveyId, 
    		@RequestParam(required = true) String id, 
            @RequestParam(required = true) String options) throws Exception{
        JSONObject object = new JSONObject();
        UcaSurveyOption po = new UcaSurveyOption();
        po.setSurveyId(Integer.parseInt(surveyId));
        po.setId(Integer.parseInt(id));
        po.setOptions(options);
        ucaSurveyOptionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaSurveyOptionService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaSurveyOptionService.delete(object, id);
        return object;
    }

}
