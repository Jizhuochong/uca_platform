package com.uca.statistic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.statistic.pojos.UcaOrgArchivesStatistic;
import com.uca.statistic.service.OrgArchivesStatisticService;
import com.uca.statistic.vo.UcaOrgArchivesStatisticVo;

@Controller
@RequestMapping("/orgarchivesstatistics/*")
public class UcaOrgArchivesStatisticController extends BaseController<UcaOrgArchivesStatisticVo> {

    @Autowired
    private OrgArchivesStatisticService orgArchivesStatisticService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/statistics/org_archives_statistics_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject list() throws Exception {
        Page<UcaOrgArchivesStatisticVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        orgArchivesStatisticService.find(object, page);
        return object;
    }

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public String index() throws Exception {
        return "uca/statistics/org_archives_statistics";
    }

    @RequestMapping(value = "/statistics", method = { RequestMethod.GET, RequestMethod.POST })
    public String statistics(@RequestParam(required = true) String sd, @RequestParam(required = true) String ed,
            Model model) throws Exception {
        UcaOrgArchivesStatistic ucaOrgArchivesStatistic = orgArchivesStatisticService.find(sd, ed);
        if (null != ucaOrgArchivesStatistic) {
            model.addAttribute("object", ucaOrgArchivesStatistic);
        }
        return "uca/statistics/org_archives_statistics";
    }

    @RequestMapping(value = "/commit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject commit(@ModelAttribute UcaOrgArchivesStatistic po) throws Exception {
        JSONObject object = new JSONObject();
        orgArchivesStatisticService.saveOrUpdate(object, po);
        return object;
    }

}
