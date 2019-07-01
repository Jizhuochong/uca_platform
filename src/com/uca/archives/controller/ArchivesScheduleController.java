package com.uca.archives.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.constant.CoreConstant;
import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.catalog.service.ArchivesCatalogService;

@Controller
@RequestMapping("/archives_schedule")
public class ArchivesScheduleController extends BaseController<UcaArchives>{
	@Autowired
	private ArchivesService archivesService;
	
	@Autowired
	private ArchivesCatalogService archivesCatalogService;
	
	/**
	 * 复制进度首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/index", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/uca/archives/archives_schedule_index");
		return modelAndView;
	}
	
	/**
	 * 复制进度列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@ResponseBody
	public String list(@RequestParam(required = true) String orderNum) throws Exception {
		// 分页查询
//		ImgUser imgUser = (ImgUser)this.getSession().getAttribute(ImageRepositoryConst.IUSER_SESSION);
		Page<UcaArchives> page = this.getPageForJpage();
		this.archivesService.findArchivesSchedule(orderNum, page);
		return this.retJsonData(page);// 返回json格式的分页列表内容
	}
	
	/**
	 * 通知退档首页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/backIndex", method = {RequestMethod.GET, RequestMethod.POST })
	public ModelAndView backIndex() throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/uca/archives/archives_she_back_index");
		return modelAndView;
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
		Page<UcaArchives> page = this.getPageForJpage();
		this.archivesService.findArchivesScheduleBack(page);
		return this.retJsonData(page);// 返回json格式的分页列表内容
	}
}
