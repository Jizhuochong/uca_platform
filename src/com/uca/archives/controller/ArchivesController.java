package com.uca.archives.controller;

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
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;
import com.uca.ucasecurity.service.UmOrgService;

@Controller
@RequestMapping("/archives")
public class ArchivesController extends BaseController<UcaArchivesVo> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private ArchivesService archivesService;

    @Autowired
    private UmOrgService orgService;

    @RequestMapping(value = "/index/{type}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index(@PathVariable("type") int type) throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("type", type == 1 || type == 2 ? type : 1);// 默认为工程档案
        modelAndView.addObject("orgList", this.orgService.tree(UcaConstants.UM_ORG_ROOT_ID, String.valueOf(type)));
        modelAndView.setViewName("/uca/archives/archives_index");
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = true) Integer type) throws Exception {
        Page<UcaArchivesVo> page = this.getPageRows();
        JSONObject object = new JSONObject();
        this.archivesService.findArchives(object, page, type);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping("/toAdd/{type}")
    public ModelAndView toAdd(@PathVariable("type") int type) throws Exception {
        UcaArchives po = new UcaArchives();
        po.setType(type);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("po", po);
        modelAndView.setViewName("/uca/archives/archives_upload");
        return modelAndView;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = false) Integer id,
            @RequestParam(required = false) Integer type) throws Exception {
        JSONObject object = new JSONObject();
        this.archivesService.getArchivesById(object, id, type);
        return object;
    }

    @RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject saveArchives(@RequestParam(required = false) String archivesId,
            @RequestParam(required = false) Integer checkStatus, @RequestParam(required = false) String fileUrl,
            @RequestParam(required = false) String sourceFileName,
            @RequestParam(required = false) String handingPersonId, @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String projectName) throws Exception {
        JSONObject object = new JSONObject();
        UcaArchives po = new UcaArchives();
        po.setArchivesId(StringUtils.isNotEmpty(archivesId) ? Integer.valueOf(archivesId) : null);
        po.setCheckStatus(checkStatus);
        po.setFileUrl(fileUrl);
        po.setSourceFileName(sourceFileName);
        po.setHandingPersonId(StringUtils.isNotEmpty(handingPersonId) ? Integer.valueOf(handingPersonId) : null);
        po.setType(type);
        // if (type == 1) {
        po.setProjectName(projectName);
        // }
        this.archivesService.saveOrUpdateArchives(object, po);
        return object;
    }
}
