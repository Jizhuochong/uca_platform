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
import com.uca.exam.pojos.UcaExaminationPaper;
import com.uca.exam.service.UcaExamPaperService;

@Controller
@RequestMapping("/exampaper/*")
public class UcaExamPaperController extends BaseController<UcaExaminationPaper> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UcaExamPaperService ucaExamPaperService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/exampaper_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String epName, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaExaminationPaper> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        ucaExamPaperService.find(object, page, epName, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String epName, 
            @RequestParam(required = true) String examTime, 
            @RequestParam(required = true) String passScore, 
            @RequestParam(required = true) String period) throws Exception{
        JSONObject object = new JSONObject();
        UcaExaminationPaper po = new UcaExaminationPaper();
        po.setEpName(epName);
        po.setExamTime(Integer.parseInt(examTime));
        po.setPassScore(Integer.parseInt(passScore));
        po.setPeriod(Integer.parseInt(period));
        ucaExamPaperService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String epId, 
    		@RequestParam(required = true) String epName, 
            @RequestParam(required = true) String examTime, 
            @RequestParam(required = true) String passScore, 
            @RequestParam(required = true) String period) throws Exception{
        JSONObject object = new JSONObject();
        UcaExaminationPaper po = new UcaExaminationPaper();
        po.setEpId(Integer.parseInt(epId));
        po.setEpName(epName);
        po.setExamTime(Integer.parseInt(examTime));
        po.setPassScore(Integer.parseInt(passScore));
        po.setPeriod(Integer.parseInt(period));
        ucaExamPaperService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamPaperService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int epId) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamPaperService.delete(object, epId);
        return object;
    }
    
    @RequestMapping(value = "/publish", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject publish(@RequestParam(required = true) int epId) throws Exception{
        JSONObject object = new JSONObject();
        ucaExamPaperService.publish(object, epId);
        return object;
    }

}
