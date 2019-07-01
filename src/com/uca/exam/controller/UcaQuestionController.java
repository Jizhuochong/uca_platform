package com.uca.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.exam.pojos.UcaQuestion;
import com.uca.exam.service.UcaQuestionService;

@Controller
@RequestMapping("/question/*")
public class UcaQuestionController extends BaseController<UcaQuestion> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UcaQuestionService ucaQuestionService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/question_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String classifyId, 
            @RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaQuestion> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaQuestionService.find(object, page, name, classifyId, type, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String classifyId, 
    		@RequestParam(required = true) String type, 
            @RequestParam(required = true) String name, 
            @RequestParam(required = false) String options, 
            @RequestParam(required = false) String answer) throws Exception{
        JSONObject object = new JSONObject();
        UcaQuestion po = new UcaQuestion();
        po.setClassifyId(Integer.parseInt(classifyId));
        po.setType(Integer.parseInt(type));
        po.setName(name);
        po.setOptions(options);
        po.setAnswer(answer);
        ucaQuestionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String questionId, 
    		@RequestParam(required = true) String classifyId, 
    		@RequestParam(required = true) String type, 
            @RequestParam(required = true) String name, 
            @RequestParam(required = false) String options, 
            @RequestParam(required = false) String answer) throws Exception{
        JSONObject object = new JSONObject();
        UcaQuestion po = new UcaQuestion();
        po.setQuestionId(Integer.parseInt(questionId));
        po.setClassifyId(Integer.parseInt(classifyId));
        po.setType(Integer.parseInt(type));
        po.setName(name);
        po.setOptions(options);
        po.setAnswer(answer);
        ucaQuestionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaQuestionService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int questionId) throws Exception{
        JSONObject object = new JSONObject();
        ucaQuestionService.delete(object, questionId);
        return object;
    }
    
    @RequestMapping(value = "/getQuestionByType", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONArray getQuestionByType(@RequestParam(required = true) int type, 
    		@RequestParam(required = false) String classifyId) throws Exception {
        List<UcaQuestion> lists = ucaQuestionService.getQuestionByType(type, classifyId);
        JSONArray array = new JSONArray();
        for(UcaQuestion uq : lists) {
            JSONObject obj = new JSONObject();
            obj.put("id", uq.getQuestionId());
            obj.put("text", uq.getName());
            array.add(obj);
        }
        return array;
    }

}
