package com.uca.archives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;

@Controller
@RequestMapping("/checkresult/*")
public class CheckResultController extends BaseController<UcaArchivesVo>{
	
	@Autowired
	private ArchivesService archivesService;
	
	@RequestMapping(value = "/auditing", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject checkresultAuditing(@RequestParam(required = true) String id, 
            @RequestParam(required = true) String pass, 
            @RequestParam(required = false) String desc) throws Exception{
	    JSONObject object = new JSONObject();
        archivesService.checkresultAuditing(id, pass, desc, object);
        return object;
    }

}
