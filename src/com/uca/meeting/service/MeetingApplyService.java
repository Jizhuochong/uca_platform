package com.uca.meeting.service;

import java.text.ParseException;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.meeting.pojos.UcaMrApply;
import com.uca.meeting.vo.UcaMrApplyStatisticsVo;

public interface MeetingApplyService {
    /**
     * 根据ID获取会议室预约记录
     * 
     * @param object
     * @param applyId
     */
    void getMrApply(JSONObject object, Integer applyId);

    /**
     * 保存和取消会议室预约记录
     * 
     * @param object
     * @param po
     * @throws ParseException
     * @throws NumberFormatException
     */
    void saveOrRemoveMrApply(JSONObject object, UcaMrApply po) throws NumberFormatException, ParseException;

    /**
     * 查询会议室预约列表
     * 
     * @param object
     * @param page
     * @param id
     * @throws ParseException
     */
    void findMrApply(JSONObject object, Integer id, String qDate) throws ParseException;

    /**
     * 会议室预约统计
     * 
     * @param object
     * @param type
     * @param mrId
     * @param bgTime
     * @param endTime
     */
    void findMrApplyForStatistics(JSONObject object, Page<UcaMrApplyStatisticsVo> page, Integer type, Integer mrId,
            String bgTime, String endTime);

    void findStatisticsMeetUser(JSONObject object, String bgTime, String endTime);

    void getMeetNoRead(JSONObject object);

    void updateMeetRead(JSONObject object, Integer applyUserId, Integer applyId);
}
