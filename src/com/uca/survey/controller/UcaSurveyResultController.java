package com.uca.survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;

import com.alibaba.fastjson.JSONObject;
import com.uca.survey.pojos.UcaSurvey;
import com.uca.survey.pojos.UcaSurveyResult;
import com.uca.survey.service.UcaSurveyResultService;
import com.uca.survey.service.UcaSurveyService;

@Controller
@RequestMapping("/surveyresult/*")
public class UcaSurveyResultController extends BaseController<UcaSurveyResult> {
    
	@Autowired
    private UcaSurveyService ucaSurveyService;
	
    @Autowired
    private UcaSurveyResultService ucaSurveyResultService;
    
    @RequestMapping(value = "/index", method = { RequestMethod.GET,RequestMethod.POST })
    public String index(@RequestParam(required = true) int surveyId, Model model) throws Exception {
    	UcaSurvey ucaSurvey = ucaSurveyService.getById(surveyId);
    	model.addAttribute("ucaSurvey", ucaSurvey);
        return "uca/survey/surveyresult_index";
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) int surveyId, 
    		@RequestParam(required = true) String result) throws Exception {
    	JSONObject object = new JSONObject();
    	UcaSurveyResult po = new UcaSurveyResult();
        po.setSurveyId(surveyId);
        po.setResult(result);
        ucaSurveyResultService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/statistics", method = { RequestMethod.GET,RequestMethod.POST })
    public String statistics(@RequestParam(required = true) int surveyId, Model model) throws Exception {
    	UcaSurvey ucaSurvey = ucaSurveyService.getById(surveyId);
    	model.addAttribute("ucaSurvey", ucaSurvey);
    	
    	List<UcaSurveyResult> surveyResultList = ucaSurveyResultService.findSurveyResultBySurveyId(surveyId);
    	model.addAttribute("surveyResultList", surveyResultList);
        return "uca/survey/surveyresult_statistics";
    }

}
