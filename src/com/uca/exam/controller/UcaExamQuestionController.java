package com.uca.exam.controller;

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
import com.uca.exam.pojos.UcaExaminationQuestion;
import com.uca.exam.service.UcaExamQuestionService;

@Controller
@RequestMapping("/examquestion/*")
public class UcaExamQuestionController extends BaseController<UcaExaminationQuestion> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UcaExamQuestionService ucaExamQuestionService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList(@RequestParam(required = true) int epId, Model model) throws Exception {
    	model.addAttribute("epId", epId);
        return "uca/exam/examquestion_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) int epId, 
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false) String classifyId, 
            @RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaExaminationQuestion> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaExamQuestionService.find(object, page, epId, name, classifyId, type, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String epId, 
    		@RequestParam(required = true) String classifyId, 
            @RequestParam(required = true) String eqNumber, 
            @RequestParam(required = true) String questionId, 
            @RequestParam(required = true) String score) throws Exception{
        JSONObject object = new JSONObject();
        UcaExaminationQuestion po = new UcaExaminationQuestion();
        po.setEpId(Integer.parseInt(epId));
        po.setClassifyId(Integer.parseInt(classifyId));
        po.setEqNumber(Integer.parseInt(eqNumber));
        po.setQuestionId(Integer.parseInt(questionId));
        po.setScore(Integer.parseInt(score));
        ucaExamQuestionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String eqId, 
    		@RequestParam(required = true) String epId, 
    		@RequestParam(required = true) String classifyId, 
            @RequestParam(required = true) String eqNumber, 
            @RequestParam(required = true) String questionId, 
            @RequestParam(required = true) String score) throws Exception{
        JSONObject object = new JSONObject();
        UcaExaminationQuestion po = new UcaExaminationQuestion();
        po.setEqId(Integer.parseInt(eqId));
        po.setEpId(Integer.parseInt(epId));
        po.setClassifyId(Integer.parseInt(classifyId));
        po.setEqNumber(Integer.parseInt(eqNumber));
        po.setQuestionId(Integer.parseInt(questionId));
        po.setScore(Integer.parseInt(score));
        ucaExamQuestionService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamQuestionService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int eqId) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamQuestionService.delete(object, eqId);
        return object;
    }

}
