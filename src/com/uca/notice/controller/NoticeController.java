package com.uca.notice.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.notice.pojos.UcaNotice;
import com.uca.notice.service.NoticeService;
import com.uca.notice.service.NoticeUserService;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.service.UmOrgService;

@Controller
@RequestMapping("/notice")
public class NoticeController extends BaseController<UcaNotice>{
	private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
		SerializerFeature.DisableCircularReferenceDetect,
		SerializerFeature.WriteMapNullValue,
		SerializerFeature.WriteNullNumberAsZero,
		SerializerFeature.WriteNullStringAsEmpty};
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private NoticeUserService noticeUserService;
	
	@Autowired
    private UmOrgService umOrgService;
	
	@RequestMapping(value = "/index/{type}", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(@PathVariable("type") int type) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("type", type == 1 || type == 2?type : 1);//默认为所有信息
		modelAndView.setViewName("/uca/notice/notice_index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) Integer type,
    		@RequestParam(required = false) String dTitle,
    		@RequestParam(required = false) String dContent) throws Exception{
		Page<UcaNotice> page = this.getPageRows();
		JSONObject object = new JSONObject();
		this.noticeService.findNotice(object, page, type, dTitle, dContent);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/showNotice", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView showNotice() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/uca/notice/show_notice_index");
		return modelAndView;
	}
	
	@RequestMapping(value = "/listShowNotice", method = { RequestMethod.POST })
    @ResponseBody
    public String listShowNotice(@RequestParam(required = false) String dTitle,
    		@RequestParam(required = false) String dContent) throws Exception{
		Page<UcaNotice> page = this.getPageRows();
		JSONObject object = new JSONObject();
		this.noticeService.findReleaseNoticeByUserId(object, page, dTitle, dContent);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/getNotice", method = { RequestMethod.POST })
    @ResponseBody
    public String getNotice() throws Exception{
		JSONObject object = new JSONObject();
		this.noticeUserService.findNotReadNoticeCountByUserId(object);
		return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
	}
	
	@RequestMapping(value = "/getObjById", method = { RequestMethod.POST })	
	@ResponseBody
	public JSONObject getObjById(@RequestParam(required = false) Integer id) throws Exception {
		JSONObject object = new JSONObject();
        this.noticeService.getNoticeById(object, id);
        List<Integer> orgList = this.noticeUserService.findReleaseNoticeForOrgList(id);
        List<UmOrg> umOrgs = umOrgService.findDatas();
        if(null != umOrgs && umOrgs.size() > 0) {
        	JSONArray jsonArray = new JSONArray();
	        for(int i=0;i<umOrgs.size();i++) {
	        	UmOrg umOrg = umOrgs.get(i);
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("id", umOrg.getOrgId());
	            jsonObject.put("name", umOrg.getOrgName());
	            jsonObject.put("checked", false);
	            for(Integer orgId : orgList){
	            	if(orgId.intValue() == umOrg.getOrgId().intValue()){
	            		jsonObject.put("checked", true);
	            		break;
	            	}
	            }
	            jsonArray.add(jsonObject);
	        }
	        object.put("orgList", jsonArray);
	    }
        return object;
	}
	
	@RequestMapping(value = "/getReadObjById", method = { RequestMethod.POST })	
	@ResponseBody
	public JSONObject getReadObjById(@RequestParam(required = false) Integer id,
			@RequestParam(required = false) Integer nId) throws Exception {
		JSONObject object = new JSONObject();
        this.noticeService.getNoticeByRead(object, id, nId);
        this.noticeUserService.updateNoticeUserForRead(nId);
        return object;
	}
	
	@RequestMapping(value="/save", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
	public JSONObject saveNotice(@RequestParam(required = false) String noticeId, 
    		@RequestParam(required = false) String title, 
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String fileUrl,
            @RequestParam(required = false) String sourceFileName,
            @RequestParam(required = false) Integer releaseStatus,
            @RequestParam(required = false) String fileFormat,
            @RequestParam(required = false) String fileSize,
            @RequestParam(required = false) Integer releaseObj,
            @RequestParam(required = false) String contactUser,
            @RequestParam(required = false) String tel,
            @RequestParam(required = false) String fax,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String releaseOrg) throws Exception{
		JSONObject object = new JSONObject();
		UcaNotice po = new UcaNotice();
		po.setNoticeId(StringUtils.isNotEmpty(noticeId)?Integer.valueOf(noticeId):null);
		po.setTitle(title);
		po.setContent(content);
		po.setFileUrl(fileUrl);
		po.setSourceFileName(sourceFileName);
		po.setReleaseStatus(releaseStatus);
		po.setFileFormat(fileFormat);
		po.setFileSize(fileSize);
		po.setReleaseObj(releaseObj);
		po.setContactUser(contactUser);
		po.setTel(tel);
		po.setFax(fax);
		po.setEmail(email);
		po.setReleaseOrg(releaseOrg);
        this.noticeService.saveOrUpdateNotice(object, po);
        return object;
	}
	
	@RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int dId) throws Exception{
        JSONObject object = new JSONObject();
        noticeService.deleteNoticeById(object, dId);
        return object;
    }
	
	@RequestMapping(value = "/deleteByRead", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject deleteByRead(@RequestParam(required = true) int dId) throws Exception{
        JSONObject object = new JSONObject();
        this.noticeUserService.deleteNoticeUserById(object, dId);
        return object;
    }
}
