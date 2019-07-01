package com.uca.duty.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.duty.pojos.UcaDuty;
import com.uca.duty.service.DutyService;

@Controller
@RequestMapping("/duty")
public class DutyController extends BaseController<UcaDuty> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
            SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullNumberAsZero, SerializerFeature.WriteNullStringAsEmpty };

    @Autowired
    private DutyService dutyService;

    @RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView index() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/uca/duty/duty_index");
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String bgTime, @RequestParam(required = false) String endTime)
            throws Exception {
        Page<UcaDuty> page = this.getPageRows();
        JSONObject object = new JSONObject();

        dutyService.findDuty(object, page, bgTime, endTime);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss", serializerFeatureNull);
    }

    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject saveDuty(@RequestParam(required = false) String dutyId,
            @RequestParam(required = false) String dutyDate, @RequestParam(required = false) String lunarCalendar,
            @RequestParam(required = false) String dayShiftUser, @RequestParam(required = false) String nightShiftUser,
            @RequestParam(required = false) String leader) throws Exception {
        JSONObject object = new JSONObject();
        UcaDuty po = new UcaDuty();
        po.setDutyId(StringUtils.isNotEmpty(dutyId) ? Integer.valueOf(dutyId) : null);
        po.setDutyDate(dutyDate != null ? DateUtil.parseDate(dutyDate) : null);
        po.setLunarCalendar(lunarCalendar != null ? DateUtil.parseDate(lunarCalendar) : null);
        po.setDayShiftUser(dayShiftUser);
        po.setNightShiftUser(nightShiftUser);
        po.setLeader(leader);
        dutyService.saveOrUpdateDuty(object, po);
        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = false) Integer id) throws Exception {
        JSONObject object = new JSONObject();
        dutyService.getByDutyId(object, id);
        return object;
    }

    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int dId) throws Exception {
        JSONObject object = new JSONObject();
        dutyService.deleteDutyById(object, dId);
        return object;
    }

    /**
     * 批量导入
     * 
     * @param file
     * @return
     * @throws Exception
     */
    // @RequestMapping(value =
    // "/importExcel",method={RequestMethod.GET,RequestMethod.POST})
    // @ResponseBody
    // public JSONObject importExcel(@RequestParam(value = "fileItem", required
    // = true) MultipartFile file) throws Exception{
    // JSONObject object = new JSONObject();
    // this.dutyService.importDuty(object, file);
    // return object;
    // }

    /**
     * 批量导入
     * 
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/importExcel", method = { RequestMethod.GET, RequestMethod.POST })
    public void importExcel(HttpServletResponse response,
            @RequestParam(value = "fileItem", required = true) MultipartFile file) throws Exception {
        JSONObject object = new JSONObject();
        this.dutyService.importDuty(object, file);
        response.setContentType("text/plain;charset=utf-8");
        response.getWriter().write(object.toString());
    }
}
