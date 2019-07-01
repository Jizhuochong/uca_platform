package com.uca.archives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.pojos.UcaArchivesBorrow;
import com.uca.archives.service.ArchivesBorrowService;
import com.uca.archives.service.ArchivesService;
import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.catalog.service.ArchivesCatalogService;

@Controller
@RequestMapping("/archives_borrow")
public class ArchivesBorrowController extends BaseController<UcaArchivesBorrow>{
	 private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
         SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
         SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	@Autowired
	private ArchivesService archivesService;
	
	@Autowired
	private ArchivesCatalogService archivesCatalogService;
	
	@Autowired
	private ArchivesBorrowService archivesBorrowService;
	
	
	@RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/archives/archives_borrow";
    }
	/**
	 * 复制进度列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String list(@RequestParam(required = false) String num,@RequestParam(required = false) String person) throws Exception {
		// 分页查询
		Page<UcaArchivesBorrow> page = this.getPageRows();
		
		JSONObject object = new JSONObject();
		this.archivesBorrowService.findArchivesSchedule(num, person, page,object);

//        archivesCatalogService.find(object, page, num, person);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
	}
	
	 @RequestMapping(value = "/save", method = { RequestMethod.POST })
	    @ResponseBody
	    public JSONObject save(@RequestParam(required = false) String num,
	            @RequestParam(required = false) String person)
	            throws Exception {
	        JSONObject object = new JSONObject();
	        UcaArchivesBorrow ab = new UcaArchivesBorrow();
	        ab.setNum(num);
	        ab.setPerson(person);
	        archivesBorrowService.saveOrUpdate(object, ab);
	        return object;
	    }

	    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
	    @ResponseBody
	    public JSONObject edit(@RequestParam(required = true) String id,
	    		@RequestParam(required = false) String num,
	            @RequestParam(required = false) String person) throws Exception {
	        JSONObject object = new JSONObject();
	        UcaArchivesBorrow ab = new UcaArchivesBorrow();
	        ab.setId(Integer.valueOf(id));
	        ab.setNum(num);
	        ab.setPerson(person);
	        archivesBorrowService.saveOrUpdate(object, ab);
	        return object;
	    }

	    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
	    @ResponseBody
	    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception {
	        JSONObject object = new JSONObject();
	        archivesBorrowService.getById(object, id);
	        return object;
	    }

	    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
	    @ResponseBody
	    public JSONObject delete(@RequestParam(required = true) int catalogId) throws Exception {
	        JSONObject object = new JSONObject();
	        archivesBorrowService.delete(object, catalogId);
	        return object;
	    }
	
	/**
	 * 通知退档列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/backList")
	@ResponseBody
	public String backList() throws Exception {
		// 分页查询
//		ImgUser imgUser = (ImgUser)this.getSession().getAttribute(ImageRepositoryConst.IUSER_SESSION);
//		Page<UcaArchives> page = this.getPageForJpage();
//		this.archivesService.findArchivesScheduleBack(page);
//		return this.retJsonData(page);// 返回json格式的分页列表内容
		return "";
	}
}
