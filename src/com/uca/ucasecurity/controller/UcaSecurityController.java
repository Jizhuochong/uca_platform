package com.uca.ucasecurity.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.pojos.BaseUser;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uca.ucasecurity.service.impl.UcaUserServiceImpl;

@Controller
@RequestMapping("/ucasecurity")
public class UcaSecurityController {
	protected static Logger logger = LoggerFactory.getLogger(UcaSecurityController.class);
	
	@Autowired
	private UcaUserServiceImpl ucaUserServiceImpl;
	
	@RequestMapping(value="/getHandingPersonList", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONArray getHandingPersonList(@RequestParam(required = true) Integer orgId) throws Exception {
		List<BaseUser> li = orgId != -1?ucaUserServiceImpl.getHandingPersonListByOrgId(orgId):ucaUserServiceImpl.getHandingPersonList();
		JSONArray array = new JSONArray();
		JSONObject initObj = new JSONObject();
		initObj.put("userId", "-1");
		initObj.put("userName", "请选择-----");
        array.add(initObj);
		for(BaseUser user : li) {
            JSONObject obj = new JSONObject();
            obj.put("userId", user.getUserId());
            obj.put("userName", user.getUserName());
            array.add(obj);
        }
        return array;
    }
}
