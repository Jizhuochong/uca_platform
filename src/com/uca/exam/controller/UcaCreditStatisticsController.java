package com.uca.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.exam.service.UcaCreditService;
import com.uca.exam.vo.UcaCreditStatisticsVo;

@Controller
@RequestMapping("/creditstatistics/*")
public class UcaCreditStatisticsController extends BaseController<UcaCreditStatisticsVo> {
    
    @Autowired
    private UcaCreditService ucaCreditService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/credit_statistics_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String type, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaCreditStatisticsVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        ucaCreditService.find(object, page, type, sd, ed);
        return object;
    }

}
