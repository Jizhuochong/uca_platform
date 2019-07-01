package com.uca.exam.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.constants.UcaConstants;
import com.uca.exam.pojos.UcaAnswer;
import com.uca.exam.pojos.UcaExaminationPaper;
import com.uca.exam.pojos.UcaExaminationQuestion;
import com.uca.exam.service.UcaAnswerService;
import com.uca.exam.service.UcaExamPaperService;
import com.uca.exam.service.UcaExamQuestionService;

@Controller
@RequestMapping("/onlineExam/*")
public class OnlineExamController extends BaseController<UcaExaminationPaper> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    private SecurityIdentifyUtils secUtils = new SecurityUtils();
    
    @Autowired
    private UcaExamPaperService ucaExamPaperService;
    
    @Autowired
    private UcaExamQuestionService ucaExamQuestionService;
    
    @Autowired
    private UcaAnswerService ucaAnswerService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/onlineexam_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list() throws Exception {
        Page<UcaExaminationPaper> page = this.getPageRows();
        JSONObject object = new JSONObject();
        ucaExamPaperService.findOnlineExam(object, page);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/start", method = { RequestMethod.GET,RequestMethod.POST })
    public String start(@RequestParam(required = true) int epId, Model model) throws Exception {
    	UcaExaminationPaper po = ucaExamPaperService.getById(epId);
    	model.addAttribute("objPo", po);
    	return "uca/exam/onlineexam_display";
    }
    
    @RequestMapping(value = "/commitExam", method = { RequestMethod.GET,RequestMethod.POST })
    public String commitExam(HttpServletRequest request, Model model) throws Exception {
    	String epId = (String) request.getParameter("epId");
    	List<UcaAnswer> isAnswerList = ucaAnswerService.getAnswerListByEpIdUserId(
    			Integer.parseInt(epId), secUtils.getUser().getUserId());
    	if(null != isAnswerList && isAnswerList.size() > 0) {
    		model.addAttribute("msg", "您之前已经答过试卷，无需再次答题！");
        	return "uca/exam/onlineexam_finish";
    	}
    	
    	List<UcaExaminationQuestion> ucaExamQuestions = ucaExamQuestionService.getExamQuestionListByEpId(Integer.parseInt(epId));
    	if(null != ucaExamQuestions && ucaExamQuestions.size() > 0) {
    		List<UcaAnswer> answerList = new ArrayList<UcaAnswer>();
    		for(UcaExaminationQuestion eq : ucaExamQuestions) {
    			UcaAnswer answer = new UcaAnswer();
    			answer.setEpId(Integer.parseInt(epId));
    			answer.setEqId(eq.getEqId());
    			answer.setAnswerUserId(secUtils.getUser().getUserId());
    			answer.setAnswerTime(new Date());
    			answer.setStatus(UcaConstants.ANSWER_STATUS_NO_READOVER);
    			
    			if(eq.getType() == UcaConstants.EXAM_QUESTION_TYPE_MULTIPLE) {
    				String multiple_selection = (String) request.getParameter("multiple_selection_"+eq.getEqId()+"_"+eq.getEqNumber());
    				answer.setAnswers(multiple_selection);
    			}
    			if(eq.getType() == UcaConstants.EXAM_QUESTION_TYPE_SINGLE) {
    				String single_selection = (String) request.getParameter("single_selection_"+eq.getEqId()+"_"+eq.getEqNumber());
    				answer.setAnswers(single_selection);
    			}
    			if(eq.getType() == UcaConstants.EXAM_QUESTION_TYPE_WENDATI) {
    				String wendati = (String) request.getParameter("wendati_"+eq.getEqId()+"_"+eq.getEqNumber());
    				answer.setAnswers(wendati);
    			}
    			
    			answerList.add(answer);
    		}
    		ucaAnswerService.saveOrUpdateList(answerList);
    	}
    	model.addAttribute("msg", "在线答题结束！");
    	return "uca/exam/onlineexam_finish";
    }
    
    @RequestMapping(value = "/getEqNumberByEpId", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getEqNumberByEpId(@RequestParam(required = true) int epId) throws Exception{
        JSONObject object = new JSONObject();
        List<String> eqNumberList = new ArrayList<String>();
        
        List<UcaExaminationQuestion> ucaExamQuestions = ucaExamQuestionService.getExamQuestionListByEpId(epId);
    	if(null != ucaExamQuestions && ucaExamQuestions.size() > 0) {
    		for(UcaExaminationQuestion eq : ucaExamQuestions) {
    			eqNumberList.add("eqNumber_select_"+eq.getEqNumber());
    		}
    	}
		object.put("eqNumberList", eqNumberList);
        return object;
    }

}
