package com.uca.apply.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.apply.pojos.UcaAuditWorkStatistics;
import com.uca.apply.service.AuditWorkStatisticsService;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.service.UmUserService;

@Controller
@RequestMapping("/auditWorkStatistics/*")
public class AuditWorkStatisticsController extends BaseController<UcaAuditWorkStatistics> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private AuditWorkStatisticsService auditWorkStatisticsService;
    @Autowired
    private UmUserService umUserService;

    @RequestMapping(value = "/toList", method = { RequestMethod.GET, RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/apply/audit_work_statistics_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String projectName, @RequestParam(required = false) String sd,
            @RequestParam(required = false) String ed) throws Exception {
        Page<UcaAuditWorkStatistics> page = this.getPageRows();
        JSONObject object = new JSONObject();

        auditWorkStatisticsService.find(object, page, projectName, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String projectName,
            @RequestParam(required = true) String picNum, @RequestParam(required = true) String picSize,
            @RequestParam(required = true) String movMinute, @RequestParam(required = true) String movSize)
            throws Exception {
        JSONObject object = new JSONObject();
        UcaAuditWorkStatistics po = new UcaAuditWorkStatistics();
        po.setProjectName(projectName);
        po.setPicNum(Integer.parseInt(picNum));
        po.setPicSize(Double.parseDouble(picSize));
        po.setMovMinute(Integer.parseInt(movMinute));
        po.setMovSize(Double.parseDouble(movSize));
        auditWorkStatisticsService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String workId,
            @RequestParam(required = true) String projectName, @RequestParam(required = true) String picNum,
            @RequestParam(required = true) String picSize, @RequestParam(required = true) String movMinute,
            @RequestParam(required = true) String movSize) throws Exception {
        JSONObject object = new JSONObject();
        UcaAuditWorkStatistics po = new UcaAuditWorkStatistics();
        po.setWorkId(Integer.parseInt(workId));
        po.setProjectName(projectName);
        po.setPicNum(Integer.parseInt(picNum));
        po.setPicSize(Double.parseDouble(picSize));
        po.setMovMinute(Integer.parseInt(movMinute));
        po.setMovSize(Double.parseDouble(movSize));
        auditWorkStatisticsService.saveOrUpdate(object, po);
        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception {
        JSONObject object = new JSONObject();
        auditWorkStatisticsService.getById(object, id);
        return object;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int workId) throws Exception {
        JSONObject object = new JSONObject();
        auditWorkStatisticsService.delete(object, workId);
        return object;
    }

    @RequestMapping(value = "/toListIndex", method = { RequestMethod.GET, RequestMethod.POST })
    public String toListIndex() throws Exception {
        return "uca/apply/audit_work_user_index";
    }

    @RequestMapping(value = "/listIndex", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String listIndex(@RequestParam(required = false) String userId, @RequestParam(required = false) String sd,
            @RequestParam(required = false) String ed) throws Exception {
        JSONObject object = new JSONObject();
        auditWorkStatisticsService.find(object, userId, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/fkComboDataByOrg/{orgId}", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONArray getFkComboDataByOrg(@PathVariable("orgId") int orgId) {
        JSONArray jsonArray = new JSONArray();
        List<UmUser> users = umUserService.getUserByOrg(orgId);
        if (null != users && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                UmUser umUser = users.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", umUser.getUserId());
                jsonObject.put("name", umUser.getUserName());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

}
