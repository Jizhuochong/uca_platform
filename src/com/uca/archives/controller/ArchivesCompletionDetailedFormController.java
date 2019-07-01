package com.uca.archives.controller;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.pojos.ArchivesCompletionDetailedForm;
import com.uca.archives.service.ArchivesCompletionDetailedFormService;

@Controller
@RequestMapping("/detailedForm/*")
public class ArchivesCompletionDetailedFormController extends BaseController<ArchivesCompletionDetailedForm> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private ArchivesCompletionDetailedFormService archivesCompletionDetailedFormService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList(@RequestParam(required = true) int registerId,
            @RequestParam(required = true) int registerType, Model model) throws Exception {
        model.addAttribute("registerId", registerId);
        model.addAttribute("registerType", registerType);
        return "uca/archives/archives_completion_detailed_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) int registerId) throws Exception {
        Page<ArchivesCompletionDetailedForm> page = this.getPageRows();
        JSONObject object = new JSONObject();

        archivesCompletionDetailedFormService.find(object, page, registerId);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@ModelAttribute ArchivesCompletionDetailedForm po) {
        JSONObject object = new JSONObject();
        archivesCompletionDetailedFormService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@ModelAttribute ArchivesCompletionDetailedForm po) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionDetailedFormService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionDetailedFormService.getById(object, id);
        return object;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        archivesCompletionDetailedFormService.delete(object, id);
        return object;
    }

}
