package com.uca.survey.controller;

import java.util.ArrayList;
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
import com.uca.survey.pojos.UcaSurveyOption;
import com.uca.survey.pojos.UcaSurveyOptionResult;
import com.uca.survey.service.UcaSurveyOptionResultService;
import com.uca.survey.service.UcaSurveyOptionService;
import com.uca.survey.service.UcaSurveyService;

@Controller
@RequestMapping("/surveyoptionresult/*")
public class UcaSurveyOptionResultController extends BaseController<UcaSurveyOptionResult> {
    
	@Autowired
    private UcaSurveyService ucaSurveyService;
	@Autowired
    private UcaSurveyOptionService ucaSurveyOptionService;
    @Autowired
    private UcaSurveyOptionResultService ucaSurveyOptionResultService;
    
    @RequestMapping(value = "/index", method = { RequestMethod.GET,RequestMethod.POST })
    public String index(@RequestParam(required = true) int surveyId, Model model) throws Exception {
    	UcaSurvey ucaSurvey = ucaSurveyService.getById(surveyId);
    	model.addAttribute("ucaSurvey", ucaSurvey);
    	
    	List<UcaSurveyOption> surveyOptionList = ucaSurveyOptionService.getSurveyOptionListBySurveyId(surveyId);
    	if(null != surveyOptionList && surveyOptionList.size() > 0) {
    		model.addAttribute("surveyOptionList", surveyOptionList);
    	} else {
    		model.addAttribute("surveyOptionList", new ArrayList<UcaSurveyOption>());
    	}
        return "uca/survey/surveyoptionresult_index";
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) int surveyId, 
    		@RequestParam(required = true) String surveyOptionIds) throws Exception {
    	JSONObject object = new JSONObject();
    	ucaSurveyOptionResultService.saveOrUpdate(object, surveyId, surveyOptionIds);
        return object;
    }
    
    @RequestMapping(value = "/statistics", method = { RequestMethod.GET,RequestMethod.POST })
    public String statistics(@RequestParam(required = true) int surveyId, Model model) throws Exception {
    	UcaSurvey ucaSurvey = ucaSurveyService.getById(surveyId);
    	model.addAttribute("ucaSurvey", ucaSurvey);
    	
    	List<UcaSurveyOption> surveyOptionList = ucaSurveyOptionService.getSurveyOptionStatisticsBySurveyId(surveyId);
    	model.addAttribute("surveyOptionList", surveyOptionList);
        return "uca/survey/surveyoptionresult_statistics";
    }

}
