package com.uca.apply.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.apply.service.ApplyRecordService;
import com.uca.apply.vo.UcaApplyQueryRecordVo;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.constants.UcaConstants;

@Controller
@RequestMapping("/wStatistics/*")
public class ArchivesWStatisticsController extends BaseController<UcaApplyQueryRecordVo> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private ArchivesService archivesService;
    @Autowired
    private ApplyRecordService applyRecordService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/apply/archives_w_statistics";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String status, @RequestParam(required = false) String sd,
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaApplyQueryRecordVo> page = this.getPageRows();
        JSONObject object = new JSONObject();

        applyRecordService.find(object, page, sd, ed, UcaConstants.ARCHIVES_TYPE_SOUND);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/soundBookingWorkload", method = { RequestMethod.GET, RequestMethod.POST })
    public String getSoundBookingWorkload(@RequestParam(required = false) String status,
            @RequestParam(required = false) String sd, @RequestParam(required = false) String ed, Model model)
            throws Exception {
        if (ParameterUtil.isNull(status)) {
            List<UcaArchives> archives = new ArrayList<UcaArchives>();
            model.addAttribute("archives", archives);
            model.addAttribute("archivesCount", 0);
        } else {
            List<UcaArchives> archives = archivesService.findArchivesSoundBookingWorkload(status, sd, ed,
                    UcaConstants.ARCHIVES_TYPE_SOUND);
            if (null == archives) {
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

}
