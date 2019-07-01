package com.uca.apply.service.impl;

import java.math.BigInteger;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.apply.dao.ApplyRecordDao;
import com.uca.apply.pojos.UcaApplyQueryRecord;
import com.uca.apply.service.ApplyRecordService;
import com.uca.apply.vo.UcaApplyQueryRecordVo;
import com.uca.archives.dao.ArchivesDao;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.vo.UcaArchivesVo;
import com.uca.constants.UcaConstants;

@Service
public class ApplyRecordServiceImpl implements ApplyRecordService {
    @Autowired
    private ApplyRecordDao applyRecordDao;
    @Autowired
    private ArchivesDao archivesDao;

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Override
    public UcaApplyQueryRecord getObjById(int aqId) {
        return applyRecordDao.queryById(aqId);
    }

    @Override
    public void findApplyRecordAuditing(JSONObject object, Page<UcaApplyQueryRecord> page, int type) {
        // TODO Auto-generated method stub
        Criteria crit = applyRecordDao.getSession().createCriteria(UcaApplyQueryRecord.class);
        crit.add(Restrictions.eq("checkStatus", type));

        int total = crit.list().size();

        crit.addOrder(Order.desc("applyTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaApplyQueryRecord> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            for (UcaApplyQueryRecord applyRecord : lists) {
                if (null != applyRecord.getUcaArchives()) {
                    applyRecord.setArchivesId(applyRecord.getUcaArchives().getArchivesId());
                    applyRecord.setOrderNum(applyRecord.getUcaArchives().getOrderNum());
                    applyRecord.setProjectName(applyRecord.getUcaArchives().getProjectName());
                }
                applyRecord.setUcaArchives(null);
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaApplyQueryRecord>());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void pass(JSONObject object, int aqId, String passTxt) {
        // TODO Auto-generated method stub
        UcaApplyQueryRecord applyRecord = getObjById(aqId);
        if (null == applyRecord) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        UcaArchives ucaArchives = applyRecord.getUcaArchives();

        applyRecord.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_PASS);
        applyRecord.setInstruction(passTxt);
        applyRecord.setCheckUserId(secUtils.getUser().getUserId());
        applyRecord.setCheckTime(new Date());
        applyRecordDao.saveOrUpdate(applyRecord);

        ucaArchives.setQueryStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
        archivesDao.saveOrUpdate(ucaArchives);

        object.put("success", true);
        object.put("msg", "审核通过！");
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void unpass(JSONObject object, int aqId, String unPassTxt) {
        // TODO Auto-generated method stub
        UcaApplyQueryRecord applyRecord = getObjById(aqId);
        if (null == applyRecord) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        UcaArchives ucaArchives = applyRecord.getUcaArchives();

        applyRecord.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_FAILED);
        applyRecord.setInstruction(unPassTxt);
        applyRecord.setCheckUserId(secUtils.getUser().getUserId());
        applyRecord.setCheckTime(new Date());
        applyRecordDao.saveOrUpdate(applyRecord);

        ucaArchives.setQueryStatus(UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO);
        archivesDao.saveOrUpdate(ucaArchives);

        object.put("success", true);
        object.put("msg", "审核不通过！");
    }

    /**
     * 预约列表
     */
    @Override
    public void findArchivesForApply(JSONObject object, Page<UcaArchivesVo> page, Integer type) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select ua.archives_id,ua.order_num,ua.upload_time,ua.query_status,ua.project_name from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status =3 order by ua.query_status desc,ua.upload_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        BaseUser user = secUtils.getUser();
        SQLQuery query = this.archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setArchivesId(Integer.valueOf(obj[0].toString()));
                vo.setOrderNum(obj[1] != null ? obj[1].toString() : "");
                vo.setUploadTime(obj[2] != null ? DateUtil.DateToStr((Date) obj[2]) : "");
                vo.setQueryStatus(Integer.valueOf(obj[3].toString()));
                vo.setProjectName(obj[4] != null ? obj[4].toString() : "");
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_archives as ua inner join um_user as u on ua.upload_user_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status = 3";
        query = this.archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    public static void main(String[] args) {
        System.out.println(ParameterUtil.isNotBlank("2013-02-01 13:40:00"));
    }

    /**
     * 预约
     * 
     * @param object
     * @param po
     */
    @Override
    public void updateApplyArchives(JSONObject object, UcaArchives po) {
        // TODO Auto-generated method stub
        UcaArchives ua = this.archivesDao.queryById(po.getArchivesId());
        if (ua != null) {
            if (po.getQueryStatus() == UcaConstants.APPLY_QUERY_RECORD_STATUS_ONE) {// 预约
                if (ua.getQueryStatus() == UcaConstants.APPLY_QUERY_RECORD_STATUS_ONE) {// 已预约
                    object.put("success", false);
                    object.put("msg", "该档案只能预约一次，请勿重复申请！");
                    return;
                }
                UcaApplyQueryRecord uaqrPo = new UcaApplyQueryRecord();
                uaqrPo.setCheckStatus(UcaConstants.ARCHIVES_CHECK_STATUS_SENDING);
                if (ParameterUtil.isNotBlank(po.getApplyTime())) {
                    try {
                        uaqrPo.setApplyTime(DateUtil.str2Date(po.getApplyTime(), "yyyy-MM-dd HH:mm:ss"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                uaqrPo.setUcaArchives(ua);
                this.applyRecordDao.save(uaqrPo);
                object.put("msg", "预约申请成功！");
            } else if (po.getQueryStatus() == UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO) {// 取消预约
                if (ua.getQueryStatus() == UcaConstants.APPLY_QUERY_RECORD_STATUS_TWO) {// 已取消预约
                    object.put("success", false);
                    object.put("msg", "该档案已取消预约，请勿重复操作！");
                    return;
                }
                // 删除对应的申请记录
                String sql = "select aqr1.aq_id,aqr1.check_status from uca_apply_query_record as aqr1 inner join (select archives_id,max(apply_time) as apply_time from uca_apply_query_record where archives_id =:archivesId) as aqr2 on aqr1.archives_id = aqr2.archives_id and aqr1.apply_time = aqr2.apply_time";
                SQLQuery query = this.applyRecordDao.getSession().createSQLQuery(sql);
                query.setParameter("archivesId", po.getArchivesId());
                List<Object[]> list = query.list();
                if (list.size() == 1) {
                    Object[] obj = list.get(0);
                    Integer checkStatus = Integer.valueOf(obj[1].toString());
                    if (checkStatus != 1) {
                        object.put("success", false);
                        object.put("msg", "该档案已执行审核操作，无法取消预约！");
                        return;
                    } else {
                        // 执行删除操作
                        sql = "delete from uca_apply_query_record where aq_id=:aqId";
                        query = this.applyRecordDao.getSession().createSQLQuery(sql);
                        query.setParameter("aqId", Integer.valueOf(obj[0].toString()));
                        query.executeUpdate();
                    }
                }
                object.put("msg", "预约取消成功！");
            }
            ua.setQueryStatus(po.getQueryStatus());
            this.archivesDao.update(ua);
            object.put("success", true);
        } else {
            object.put("success", false);
            object.put("msg", "选择的记录不存在！");
        }
    }

    /**
     * 预约审核结果列表
     */
    @Override
    public void findApplyRecordForQuery(JSONObject object, Page<UcaArchivesVo> page, Integer type) {
        // TODO Auto-generated method stub
        StringBuilder sql = new StringBuilder(
                "select ua.order_num,aqr.apply_time,aqr.check_status,aqr.check_time,aqr.instruction,ua.project_name from uca_apply_query_record aqr inner join uca_archives as ua on aqr.archives_id = ua.archives_id inner join um_user as u on ua.upload_user_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status =3 order by aqr.check_status asc,aqr.apply_time desc");
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        BaseUser user = secUtils.getUser();
        SQLQuery query = this.archivesDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List<Object[]> list = query.list();
        List<UcaArchivesVo> listResult = new ArrayList<UcaArchivesVo>(list.size());
        UcaArchivesVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaArchivesVo();
                vo.setOrderNum(obj[0] != null ? obj[0].toString() : "");
                vo.setApplyTime(obj[1] != null ? DateUtil.DateToStr((Date) obj[1]) : "");
                vo.setCheckStatus(Integer.valueOf(obj[2].toString()));
                vo.setCheckTime(obj[3] != null ? DateUtil.DateToStr((Date) obj[3]) : "");
                vo.setInstruction(obj[4] != null ? obj[4].toString() : "");
                vo.setProjectName(obj[5] != null ? obj[5].toString() : "");
                listResult.add(vo);
            }
        }

        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_apply_query_record aqr inner join uca_archives as ua on aqr.archives_id = ua.archives_id inner join um_user as u on ua.upload_user_id = u.user_id where ua.upload_user_id =:uploadUserId and ua.type=:type and ua.check_status = 3";
        query = this.archivesDao.getSession().createSQLQuery(pagingSql);
        query.setParameter("uploadUserId", user.getUserId());
        query.setParameter("type", type);

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }

    @Override
    public void find(JSONObject object, Page<UcaApplyQueryRecordVo> page, String sd, String ed, int archivesType) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select ua.project_name,uue.dev_org,uaqr.apply_time,uaqr.appointment_time,"
                + "uu.user_name,uaqr.check_time from uca_apply_query_record uaqr "
                + "left join uca_archives ua on uaqr.archives_id=ua.archives_id "
                + "left join um_user uu on uu.user_id=uaqr.check_user_Id "
                + "left join um_user_expand uue on uue.user_id=ua.upload_user_id "
                + "where ua.type=2 and ua.query_status=1 and uaqr.check_status=3");
        if (null != start) {
            sql.append(" and uaqr.apply_time>=:start");
        }
        if (null != end) {
            sql.append(" and uaqr.apply_time<=:end");
        }
        String pagingSql = " limit " + page.getPageNo() + "," + page.getPageSize();
        SQLQuery query = this.applyRecordDao.getSession().createSQLQuery(sql.append(pagingSql).toString());
        if (null != start) {
            query.setParameter("start", DateUtil.DateToFormatStr(start, "yyyy-MM-dd HH:mm:ss"));
        }
        if (null != end) {
            query.setParameter("end", DateUtil.DateToFormatStr(end, "yyyy-MM-dd HH:mm:ss"));
        }

        List<Object[]> list = query.list();
        List<UcaApplyQueryRecordVo> listResult = new ArrayList<UcaApplyQueryRecordVo>(list.size());
        UcaApplyQueryRecordVo vo = null;
        if (list != null && list.size() > 0) {
            for (Object[] obj : list) {
                vo = new UcaApplyQueryRecordVo();
                vo.setProjectName(obj[0] != null ? obj[0].toString() : "");
                vo.setDevOrg(obj[1] != null ? obj[1].toString() : "");
                vo.setApplyTime(obj[2] != null ? DateUtil.DateToStr((Date) obj[2]) : "");
                vo.setAppointmentTime(obj[3] != null ? DateUtil.DateToFormatStr((Date) obj[3], "yyyy-MM-dd") : "");
                vo.setUserName(obj[4] != null ? obj[4].toString() : "");
                vo.setCheckTime(obj[5] != null ? DateUtil.DateToStr((Date) obj[5]) : "");
                listResult.add(vo);
            }
        }
        page.setListResult(listResult);

        pagingSql = "select count(1) from uca_apply_query_record uaqr left join uca_archives ua on uaqr.archives_id=ua.archives_id left join um_user uu on uu.user_id=uaqr.check_user_Id left join um_user_expand uue on uue.user_id=ua.upload_user_id where ua.type=2 and ua.query_status=1 and uaqr.check_status=3 ";
        if (null != start) {
            pagingSql += " and uaqr.apply_time>=:start";
        }
        if (null != end) {
            pagingSql += " and uaqr.apply_time<=:end";
        }
        query = this.applyRecordDao.getSession().createSQLQuery(pagingSql);
        if (null != start) {
            query.setParameter("start", DateUtil.DateToFormatStr(start, "yyyy-MM-dd HH:mm:ss"));
        }
        if (null != end) {
            query.setParameter("end", DateUtil.DateToFormatStr(end, "yyyy-MM-dd HH:mm:ss"));
        }

        List countList = query.list();
        object.put("rows", page.getListResult());
        object.put("total", (BigInteger) countList.get(0));
    }
}