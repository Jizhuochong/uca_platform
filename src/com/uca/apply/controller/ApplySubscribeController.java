package com.uca.apply.controller;

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
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.apply.service.ApplyRecordService;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.vo.UcaArchivesVo;

@Controller
@RequestMapping("/applySubscribe")
public class ApplySubscribeController extends BaseController<UcaArchivesVo> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private ApplyRecordService applyRecordService;

    @RequestMapping(value = "/applyIndex/{type}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView applyIndex(@PathVariable("type") int type) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", type == 2 ? type : 2);// 默认为声像档案
        modelAndView.setViewName("/uca/apply/apply_subscribe_index");
        return modelAndView;
    }

    @RequestMapping(value = "/applyList", method = { RequestMethod.POST })
    @ResponseBody
    public String applyList(@RequestParam(required = true) Integer type) throws Exception {
        Page<UcaArchivesVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        this.applyRecordService.findArchivesForApply(object, page, type);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/apply", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject apply(@RequestParam(required = true) Integer archivesId,
            @RequestParam(required = true) Integer queryStatus, @RequestParam(required = false) String applyTime)
            throws Exception {
        JSONObject object = new JSONObject();
        UcaArchives po = new UcaArchives();
        po.setArchivesId(archivesId);
        po.setQueryStatus(queryStatus);
        if (ParameterUtil.isNotBlank(applyTime)) {
            po.setApplyTime(applyTime);
        }
        this.applyRecordService.updateApplyArchives(object, po);
        return object;
    }

    @RequestMapping(value = "/queryIndex/{type}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView queryIndex(@PathVariable("type") int type) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", type == 2 ? type : 2);// 默认为声像档案
        modelAndView.setViewName("/uca/apply/apply_query_index");
        return modelAndView;
    }

    @RequestMapping(value = "/queryList", method = { RequestMethod.POST })
    @ResponseBody
    public String queryList(@RequestParam(required = true) Integer type) throws Exception {
        Page<UcaArchivesVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        this.applyRecordService.findApplyRecordForQuery(object, page, type);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }
}
