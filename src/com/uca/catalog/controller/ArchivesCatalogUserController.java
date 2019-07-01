package com.uca.catalog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.catalog.service.ArchivesCatalogService;

@Controller
@RequestMapping("/archives_catalog")
public class ArchivesCatalogUserController extends BaseController<UcaArchivesCatalog>{
	
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
		modelAndView.setViewName("/uca/catalog/catalog_user_index");
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
	public String list(@RequestParam(required = true) String projectName) throws Exception {
		// 分页查询
//		ImgUser imgUser = (ImgUser)this.getSession().getAttribute(ImageRepositoryConst.IUSER_SESSION);
		Page<UcaArchivesCatalog> page = this.getPageForJpage();
		this.archivesCatalogService.findCatalogForUser(projectName, page);
		return this.retJsonData(page);// 返回json格式的分页列表内容
	}
}
