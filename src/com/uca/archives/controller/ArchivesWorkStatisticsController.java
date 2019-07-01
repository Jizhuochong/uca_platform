package com.uca.archives.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;

@Controller
@RequestMapping("/workStatistics/*")
public class ArchivesWorkStatisticsController extends BaseController<UcaArchivesVo>{
	
	@Autowired
	private ArchivesService archivesService;
	
	@RequestMapping(value = "/soundBookingWorkload", method = {RequestMethod.GET, RequestMethod.POST })
	public String getSoundBookingWorkload(@RequestParam(required = false) String status, 
			@RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed, 
			Model model) throws Exception {
		if(ParameterUtil.isNull(status)) {
			List<UcaArchives> archives = new ArrayList<UcaArchives>();
			model.addAttribute("archives", archives);
			model.addAttribute("archivesCount", 0);
		} else {
			List<UcaArchives> archives = archivesService.findArchivesSoundBookingWorkload(
					status, sd, ed, UcaConstants.ARCHIVES_TYPE_SOUND);
			if(null == archives) {
				model.addAttribute("archives", new ArrayList<UcaArchives>());
				model.addAttribute("archivesCount", 0);
			} else {
				model.addAttribute("archives", archives);
				model.addAttribute("archivesCount", archives.size());
			}
		}
		model.addAttribute("sd", sd);
		model.addAttribute("ed", ed);
		return "uca/archives/archives_sound_booking_workload_statistics";
	}
	
	@RequestMapping(value = "/soundAuditingWorkload", method = {RequestMethod.GET, RequestMethod.POST })
	public String getSoundAuditingWorkload(@RequestParam(required = false) String status, 
			@RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed, 
			Model model) throws Exception {
		if(ParameterUtil.isNull(status)) {
			List<UcaArchives> archives = new ArrayList<UcaArchives>();
			model.addAttribute("archives", archives);
			model.addAttribute("archivesCount", 0);
		} else {
			List<UcaArchives> archives = archivesService.findArchivesSoundAuditingWorkload(
					status, sd, ed, UcaConstants.ARCHIVES_TYPE_SOUND);
			if(null == archives) {
				model.addAttribute("archives", new ArrayList<UcaArchives>());
				model.addAttribute("archivesCount", 0);
			} else {
				model.addAttribute("archives", archives);
				model.addAttribute("archivesCount", archives.size());
			}
		}
		model.addAttribute("sd", sd);
		model.addAttribute("ed", ed);
		return "uca/archives/archives_sound_auditing_workload_statistics";
	}

}
