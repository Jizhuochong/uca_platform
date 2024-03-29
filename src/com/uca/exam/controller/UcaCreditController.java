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
import com.uca.exam.pojos.UcaCredit;
import com.uca.exam.service.UcaCreditService;

@Controller
@RequestMapping("/credit/*")
public class UcaCreditController extends BaseController<UcaCredit> {
    
    @Autowired
    private UcaCreditService ucaCreditService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/exam/credit_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String epName, 
            @RequestParam(required = false) String userName) throws Exception {
        Page<UcaCredit> page = this.getPageRows();
        JSONObject object = new JSONObject();
        ucaCreditService.find(object, page, epName, userName);
        return object;
    }

}
