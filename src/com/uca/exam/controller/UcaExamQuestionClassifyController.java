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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uca.exam.pojos.UcaExamQuestionClassify;
import com.uca.exam.service.UcaExamQuestionClassifyService;

@Controller
@RequestMapping("/examquestionclassify/*")
public class UcaExamQuestionClassifyController extends BaseController<UcaExamQuestionClassify> {
    
    @Autowired
    private UcaExamQuestionClassifyService ucaExamQuestionClassifyService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/examquestionclassify_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String name) throws Exception {
        Page<UcaExamQuestionClassify> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaExamQuestionClassifyService.find(object, page, name);
        return object;
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String name) throws Exception{
        JSONObject object = new JSONObject();
        UcaExamQuestionClassify po = new UcaExamQuestionClassify();
        po.setName(name);
        ucaExamQuestionClassifyService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String id, 
            @RequestParam(required = true) String name) throws Exception{
        JSONObject object = new JSONObject();
        UcaExamQuestionClassify po = new UcaExamQuestionClassify();
        po.setId(Integer.parseInt(id));
        po.setName(name);
        ucaExamQuestionClassifyService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamQuestionClassifyService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamQuestionClassifyService.delete(object, id);
        return object;
    }
    
    @RequestMapping(value = "/fkComboData", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONArray getFkComboData() {
	    JSONArray jsonArray = new JSONArray();
	    List<UcaExamQuestionClassify> ucaExamQuestionClassifyList = ucaExamQuestionClassifyService.findDatas();
	    if(null != ucaExamQuestionClassifyList && ucaExamQuestionClassifyList.size() > 0) {
	        for(int i=0;i<ucaExamQuestionClassifyList.size();i++) {
	        	UcaExamQuestionClassify ucaExamQuestionClassify = ucaExamQuestionClassifyList.get(i);
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("id", ucaExamQuestionClassify.getId());
	            jsonObject.put("name", ucaExamQuestionClassify.getName());
	            jsonArray.add(jsonObject);
	        }
	    }
        return jsonArray;
    }

}
