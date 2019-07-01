package com.uca.meeting.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.meeting.dao.MeetingApplyDao;
import com.uca.meeting.dao.MeetingApplyUserDao;
import com.uca.meeting.dao.MeetingDao;
import com.uca.meeting.pojos.UcaMeetingRoom;
import com.uca.meeting.pojos.UcaMrApply;
import com.uca.meeting.pojos.UcaMrApplyUser;
import com.uca.meeting.service.MeetingApplyService;
import com.uca.meeting.vo.UcaMrApplyStatisticsVo;
import com.uca.meeting.vo.UcaMrApplyVo;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.service.UmOrgService;
import com.uca.ucasecurity.service.UmUserService;
import com.uca.utils.DateUtils;

@Service
public class MeetingApplyServiceImpl implements MeetingApplyService {
    @Autowired
    private MeetingApplyDao meetingApplyDao;

    @Autowired
    private MeetingDao meetingDao;

    @Autowired
    private UmUserService userService;
    
    @Autowired
    private UmOrgService umOrgService;

    @Autowired
    private MeetingApplyUserDao meetingApplyUserDao;

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Override
    public void getMrApply(JSONObject object, Integer applyId) {
        UcaMrApply po = this.meetingApplyDao.queryById(applyId);
        UcaMeetingRoom umPo = this.meetingDao.queryById(po.getMrId());
        po.setMeetingName(umPo.getName());
        Hibernate.initialize(po.getUcaMrApplyUser());
        if (po.getUcaMrApplyUser().size() > 0) {
            Iterator<UcaMrApplyUser> itt = po.getUcaMrApplyUser().iterator();
            UcaMrApplyUser umau = null;
            UmUser uu = null;
            while (itt.hasNext()) {
                umau = itt.next();
                uu = userService.getByIdNoStatus(umau.getUserId());
                umau.setUserName(uu.getUserName());
                umau.setUcaMrApply(null);
            }
        }
        po.setApplyDate(DateUtil.DateToFormatStr(po.getApplyTime(), "yyyy-MM-dd HH:mm:ss"));
        UmUser applyUser = userService.getByIdNoStatus(po.getApplyUserId());
        if (null != applyUser) {
            po.setApplyName(applyUser.getUserName());
            UmOrg umOrg = umOrgService.getUmOrgById(applyUser.getOrgId());
            if (null != umOrg) {
                po.setApplyOrg(umOrg.getOrgName());
            }
        }
        object.put("success", true);
        object.put("objPo", po);
    }

    @Override
    public void saveOrRemoveMrApply(JSONObject object, UcaMrApply po) throws NumberFormatException, ParseException {
        // TODO Auto-generated method stub
        String vleStr = po.getVleStr();
        String[] vleArr = vleStr.split("\\|");
        String[] hourArr = null;
        List<Criterion> cnList = new ArrayList<Criterion>(0);
        cnList.add(Restrictions.eq("mrId", po.getMrId()));
        Disjunction disjunction = Restrictions.disjunction();
        for (String vle : vleArr) {
            hourArr = vle.split(",");
            if (hourArr.length > 1) {
                disjunction.add(Restrictions.and(
                        Restrictions.ge("meetingBeginTime",
                                DateUtils.getDateCritical(po.getApplyDate(), Integer.parseInt(hourArr[0]), 0, 0)),
                        Restrictions.le(
                                "meetingEndTime",
                                DateUtils.getDateCritical(po.getApplyDate(),
                                        Integer.parseInt(hourArr[hourArr.length - 1]), 59, 59))));
            } else {
                disjunction.add(Restrictions.and(
                        Restrictions.ge("meetingBeginTime",
                                DateUtils.getDateCritical(po.getApplyDate(), Integer.parseInt(hourArr[0]), 0, 0)),
                        Restrictions.le("meetingEndTime",
                                DateUtils.getDateCritical(po.getApplyDate(), Integer.parseInt(hourArr[0]), 59, 59))));
            }
        }
        cnList.add(disjunction);
        List<UcaMrApply> umaList = meetingApplyDao.findList(cnList);
        if (UcaConstants.MEETING_ROOM_STATUS_ONE == po.getFlag()) {
            if (umaList.size() > 0) {
                object.put("success", false);
                StringBuilder msg = new StringBuilder("您选择的时间段：");
                for (UcaMrApply umaPo : umaList) {
                    msg.append(DateUtil.dateToStrLong(umaPo.getMeetingBeginTime())).append("-")
                            .append(DateUtil.dateToStrLong(umaPo.getMeetingEndTime())).append("\r\n");
                }
                msg.append("已被预约,请重新选择时间段！");
                object.put("msg", msg);
                return;
            }
            UcaMrApply umaPo = null;
            for (String vle : vleArr) {
                umaPo = new UcaMrApply();
                umaPo.setMrId(po.getMrId());
                umaPo.setApplyTime(new Date());
                umaPo.setApplyUserId(secUtils.getUser().getUserId());
                umaPo.setConferenceName(po.getConferenceName());
                umaPo.setRemark(po.getRemark());
                String devices = po.getDevices();
                if (devices.endsWith(",")) {
                    devices = devices.substring(0, devices.length() - 1);
                }
                umaPo.setDevices(devices);
                // 处理参会人员
                // umaPo.setParticipants(po.getParticipants());
                this.buildParticipants(po.getParticipants(), umaPo);

                hourArr = vle.split(",");
                if (hourArr.length > 1) {
                    umaPo.setMeetingBeginTime(DateUtils.getDateCritical(po.getApplyDate(),
                            Integer.parseInt(hourArr[0]), 0, 0));
                    umaPo.setMeetingEndTime(DateUtils.getDateCritical(po.getApplyDate(),
                            Integer.parseInt(hourArr[hourArr.length - 1]), 59, 59));
                } else {
                    umaPo.setMeetingBeginTime(DateUtils.getDateCritical(po.getApplyDate(),
                            Integer.parseInt(hourArr[0]), 0, 0));
                    umaPo.setMeetingEndTime(DateUtils.getDateCritical(po.getApplyDate(), Integer.parseInt(hourArr[0]),
                            59, 59));
                }

                meetingApplyDao.save(umaPo);
            }
            object.put("success", true);
            object.put("msg", "预约成功！");
        } else if (UcaConstants.MEETING_ROOM_STATUS_TWO == po.getFlag()) {
            if (umaList.size() == 0) {
                object.put("success", false);
                object.put("msg", "选择的记录不存在！");
                return;
            }
            meetingApplyDao.deleteAll(umaList);
            object.put("success", true);
            object.put("msg", "取消预约成功！");
        }

    }

    private void buildParticipants(String participants, UcaMrApply umaPo) {
        String[] parArr = participants.split(",");
        Set<UcaMrApplyUser> umauSet = umaPo.getUcaMrApplyUser();
        UcaMrApplyUser umauPo = null;
        for (String parS : parArr) {
            umauPo = new UcaMrApplyUser();
            umauPo.setUserId(Integer.parseInt(parS));
            umauPo.setUcaMrApply(umaPo);
            umauSet.add(umauPo);
        }
    }

    @Override
    public void findMrApply(JSONObject object, Integer id, String qDate) throws ParseException {
        // TODO Auto-generated method stub
        UcaMeetingRoom umrPo = meetingDao.queryById(id);
        if (umrPo != null && UcaConstants.MEETING_ROOM_STATUS_ONE == umrPo.getStatus()) {
            BaseUser user = secUtils.getUser();
            List<Criterion> cnList = new ArrayList<Criterion>(0);
            cnList.add(Restrictions.eq("mrId", id));
            if (StringUtils.isEmpty(qDate)) {// 未输入查询日期，则默认按照当天会议室查询
                cnList.add(Restrictions.ge("meetingBeginTime", DateUtils.getDateCriticalForBegin()));
                cnList.add(Restrictions.le("meetingEndTime", DateUtils.getDateCriticalForEnd()));
            } else {
                cnList.add(Restrictions.ge("meetingBeginTime", DateUtils.getDateCriticalForBegin(qDate)));
                cnList.add(Restrictions.le("meetingEndTime", DateUtils.getDateCriticalForEnd(qDate)));
            }
            List<UcaMrApply> umaList = meetingApplyDao.findList(cnList);
            Map<Integer, UcaMrApply> map = checkMrApply(umaList);
            List<UcaMrApplyVo> voList = new ArrayList<UcaMrApplyVo>(9);
            UcaMrApplyVo vo = null;
            UcaMrApply po = null;
            for (int i = 9; i < 18; i++) {
                vo = new UcaMrApplyVo();
                if (map.get(i) != null) {
                    po = map.get(i);
                    vo.setApplyId(po.getApplyId());
                    vo.setApplyStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_ONE);
                    vo.setApplyUserId(po.getApplyUserId());
                    vo.setBeloneStatus(po.getApplyUserId() == user.getUserId() ? UcaConstants.APPLY_QUERY_RECORD_STATUS_ONE
                            : UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
                    vo.setMrId(id);
                    vo.setWinTime(i);
                    vo.setWinTimeShow(buildShowTime(i));
                } else {
                    // vo.setApplyId(po.getApplyId());
                    vo.setApplyStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
                    // vo.setApplyUserId(po.getApplyUserId());
                    // vo.setBeloneStatus(po.getApplyUserId() ==
                    // user.getUserId()?UcaConstants.APPLY_QUERY_RECORD_STATUS_ONE:
                    // UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
                    vo.setMrId(id);
                    vo.setWinTime(i);
                    vo.setWinTimeShow(buildShowTime(i));
                }
                voList.add(vo);
            }
            object.put("success", true);
            object.put("voList", voList);
        } else {
            object.put("success", false);
            object.put("msg", "选择的记录不存在！");
        }
    }

    private String buildShowTime(int i) {
        if (i < 10) {
            return "0" + i + ":00-0" + i + ":59";
        } else {
            return i + ":00-" + i + ":59";
        }
    }

    private Map<Integer, UcaMrApply> checkMrApply(List<UcaMrApply> umaList) {
        Map<Integer, UcaMrApply> map = new HashMap<Integer, UcaMrApply>(0);
        SimpleDateFormat f = new SimpleDateFormat("HH:mm");
        String bTime = null;
        String eTime = null;
        int ind = 0;
        for (UcaMrApply po : umaList) {
            bTime = f.format(po.getMeetingBeginTime());
            eTime = f.format(po.getMeetingEndTime());
            ind = splitApplyTime(bTime, eTime);
            for (int i = 0; i < ind; i++) {
                map.put(Integer.valueOf(bTime.split(":")[0]) + i, po);
            }
        }
        return map;
    }

    private int splitApplyTime(String bTime, String eTime) {
        int ind = 0;
        String hour = DateUtil.getTwoHour(eTime, bTime);
        ind = (int) Math.rint(Double.parseDouble(hour));
        return ind;
    }

    /**
     * 会议室预约统计
     * 
     * @param object
     * @param type
     * @param mrId
     * @param bgTime
     * @param endTime
     */
    @Override
    public void findMrApplyForStatistics(JSONObject object, Page<UcaMrApplyStatisticsVo> page, Integer type,
            Integer mrId, String bgTime, String endTime) {
        // TODO Auto-generated method stub
        if (type == null) {
            object.put("rows", new ArrayList<UcaMrApplyStatisticsVo>(0));
            object.put("total", 0);
            return;
        }
        StringBuilder sql = new StringBuilder(
                "select ma.mr_id,mr.name,ma.apply_count,ma.statistics_time from (select mr_id,count(1) as apply_count,");
        String groupBySql = null;
        if (type == UcaConstants.MEETING_ROOM_APPLY_STATISTICS_WEEK) {
            sql.append("concat(date_format(apply_time, '%U'),'周') as statistics_time from uca_mr_apply where 1=1");
            groupBySql = " group by mr_id,date_format(apply_time, '%U')) as ma left join uca_meeting_room as mr on ma.mr_id = mr.mr_id";
        } else if (type == UcaConstants.MEETING_ROOM_APPLY_STATISTICS_MONTH) {
            sql.append("concat(date_format(apply_time, '%c'),'月') as statistics_time from uca_mr_apply where 1=1");
            groupBySql = " group by mr_id,date_format(apply_time, '%c')) as ma left join uca_meeting_room as mr on ma.mr_id = mr.mr_id";
        } else if (type == UcaConstants.MEETING_ROOM_APPLY_STATISTICS_YEAR) {
            sql.append("concat(date_format(apply_time, '%Y'),'年') as statistics_time from uca_mr_apply where 1=1");
            groupBySql = " group by mr_id,date_format(apply_time, '%Y')) as ma left join uca_meeting_room as mr on ma.mr_id = mr.mr_id";
        }

        if (mrId != null && mrId != -1) {
            sql.append(" and mr_id=:mrId");
        }

        if (StringUtils.isNotEmpty(bgTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" and meeting_begin_time >=:bgTime");
            sql.append(" and meeting_end_time <=:endTime");
        }

        sql.append(groupBySql);

        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();

        SQLQuery query = this.meetingApplyDao.getSession().createSQLQuery(sql.toString() + pagingSql);

        if (mrId != null && mrId != -1) {
            query.setParameter("mrId", mrId);
        }

        if (StringUtils.isNotEmpty(bgTime) && StringUtils.isNotEmpty(endTime)) {
            query.setParameter("bgTime", bgTime);
            query.setParameter("endTime", endTime);
        }
        List<Object[]> list = query.list();

        List<UcaMrApplyStatisticsVo> listResult = new ArrayList<UcaMrApplyStatisticsVo>(list.size());
        UcaMrApplyStatisticsVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaMrApplyStatisticsVo();
                vo.setMrId(Integer.valueOf(obj[0].toString()));
                vo.setName(obj[1] != null ? obj[1].toString() : "");
                vo.setApplyCount(Integer.valueOf(obj[2].toString()));
                vo.setStatisticsTime(obj[3] != null ? obj[3].toString() : "");
                listResult.add(vo);
            }
        }

        // page.setListResult(listResult);
        pagingSql = "select count(1) " + sql.substring(sql.indexOf("from"));
        query = this.meetingApplyDao.getSession().createSQLQuery(pagingSql);
        if (mrId != null && mrId != -1) {
            query.setParameter("mrId", mrId);
        }

        if (StringUtils.isNotEmpty(bgTime) && StringUtils.isNotEmpty(endTime)) {
            query.setParameter("bgTime", bgTime);
            query.setParameter("endTime", endTime);
        }

        List countList = query.list();

        object.put("rows", listResult);
        object.put("total", (BigInteger) countList.get(0));
    }
    
    @Override
    public void findStatisticsMeetUser(JSONObject object, String bgTime, String endTime) {
        StringBuilder sql = new StringBuilder(
                "select umr.name,uma.apply_id,uma.conference_name,uma.meeting_begin_time,uma.meeting_end_time "
                        + "from uca_mr_apply uma left join uca_meeting_room umr on uma.mr_id=umr.mr_id where 1=1");
        if (StringUtils.isNotEmpty(bgTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" and uma.meeting_begin_time>=:bgTime");
            sql.append(" and uma.meeting_end_time<=:endTime");
        }

        SQLQuery query = this.meetingApplyDao.getSession().createSQLQuery(sql.toString());
        if (StringUtils.isNotEmpty(bgTime) && StringUtils.isNotEmpty(endTime)) {
            query.setParameter("bgTime", bgTime);
            query.setParameter("endTime", endTime);
        }
        List<Object[]> list = query.list();

        int meetUserCount = 0;
        JSONArray array = new JSONArray();
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                JSONObject jsonObj = new JSONObject();
                jsonObj.put("meetingName", obj[0] != null ? obj[0].toString() : "");
                jsonObj.put("applyId", Integer.valueOf(obj[1].toString()));
                jsonObj.put("conferenceName", obj[2] != null ? obj[2].toString() : "");
                jsonObj.put("meetBeginTime", obj[3] != null ? obj[3].toString() : "");
                jsonObj.put("meetEndTime", obj[4] != null ? obj[4].toString() : "");

                UcaMrApply po = this.meetingApplyDao.queryById(Integer.valueOf(obj[1].toString()));
                Hibernate.initialize(po.getUcaMrApplyUser());
                jsonObj.put("meetUserNum", po.getUcaMrApplyUser().size());
                meetUserCount += po.getUcaMrApplyUser().size();

                array.add(jsonObj);
            }
        }

        object.put("meetUserCount", meetUserCount);
        object.put("rows", array);
        object.put("total", array.size());
    }

    @Override
    public void getMeetNoRead(JSONObject object) {
        StringBuilder sql = new StringBuilder("select umau.apply_user_id,umau.apply_id from uca_mr_apply_user umau "
                + "where umau.user_id=:userId and umau.flag_read=0 order by umau.apply_user_id desc limit 1");
        SQLQuery query = this.meetingApplyUserDao.getSession().createSQLQuery(sql.toString());
        query.setParameter("userId", secUtils.getUser().getUserId());

        List<Object[]> list = query.list();
        if (list != null && list.size() > 0) {
            Object[] obj = list.get(0);
            object.put("applyUserId", Integer.valueOf(obj[0].toString()));

            Integer applyId = Integer.valueOf(obj[1].toString());
            UcaMrApply po = this.meetingApplyDao.queryById(applyId);
            UcaMeetingRoom umPo = this.meetingDao.queryById(po.getMrId());
            po.setMeetingName(umPo.getName());
            po.setBeginDate(DateUtil.DateToFormatStr(po.getMeetingBeginTime(), "yyyy-MM-dd HH:mm:ss"));
            po.setEndDate(DateUtil.DateToFormatStr(po.getMeetingEndTime(), "yyyy-MM-dd HH:mm:ss"));
            po.setUcaMrApplyUser(null);

            object.put("objPo", po);
            object.put("success", true);
        } else {
            object.put("success", false);
        }
    }

    @Override
    public void updateMeetRead(JSONObject object, Integer applyUserId, Integer applyId) {
        UcaMrApply po = this.meetingApplyDao.queryById(applyId);
        Hibernate.initialize(po.getUcaMrApplyUser());
        if (po.getUcaMrApplyUser().size() > 0) {
            boolean update = false;
            Set<UcaMrApplyUser> set = po.getUcaMrApplyUser();
            for (UcaMrApplyUser umau : set) {
                if (umau.getApplyUserId() == applyUserId && umau.getUserId() == secUtils.getUser().getUserId()) {
                    umau.setFlagRead(1);
                    set.add(umau);
                    update = true;
                    break;
                }
            }
            if (update) {
                po.setUcaMrApplyUser(set);
                meetingApplyDao.saveOrUpdate(po);
            }
        }
    }

}
