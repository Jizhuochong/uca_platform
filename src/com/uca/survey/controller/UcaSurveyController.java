package com.uca.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.service.UcaSurveyService;

@Controller
@RequestMapping("/survey/*")
public class UcaSurveyController extends BaseController<UcaSurvey> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UcaSurveyService ucaSurveyService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/survey/survey_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaSurvey> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaSurveyService.find(object, page, (ParameterUtil.isNotNull(type) && 
        		ParameterUtil.isNumber(type) ? Integer.parseInt(type) : 0), sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String title, 
            @RequestParam(required = true) String type) throws Exception{
        JSONObject object = new JSONObject();
        UcaSurvey po = new UcaSurvey();
        po.setTitle(title);
        po.setType(ParameterUtil.isNotNull(type) && ParameterUtil.isNumber(type) ? Integer.parseInt(type) : 0);
        ucaSurveyService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String id, 
            @RequestParam(required = true) String title) throws Exception{
        JSONObject object = new JSONObject();
        UcaSurvey po = new UcaSurvey();
        po.setId(Integer.parseInt(id));
        po.setTitle(title);
        ucaSurveyService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaSurveyService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaSurveyService.delete(object, id);
        return object;
    }
    
    @RequestMapping(value = "/toCommitList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toCommitList() throws Exception {
        return "uca/survey/survey_commit_list";
    }
    
    @RequestMapping(value = "/commitlist", method = { RequestMethod.POST })
    @ResponseBody
    public String commitlist(@RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaSurvey> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaSurveyService.find(object, page, (ParameterUtil.isNotNull(type) && 
        		ParameterUtil.isNumber(type) ? Integer.parseInt(type) : 0), sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/toStatisticsList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toStatisticsList() throws Exception {
        return "uca/survey/survey_statistics_list";
    }
    
    @RequestMapping(value = "/statisticslist", method = { RequestMethod.POST })
    @ResponseBody
    public String statisticslist(@RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaSurvey> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaSurveyService.find(object, page, (ParameterUtil.isNotNull(type) && 
        		ParameterUtil.isNumber(type) ? Integer.parseInt(type) : 0), sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }

}
