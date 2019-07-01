package com.uca.apply.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.apply.dao.AuditWorkStatisticsDao;
import com.uca.apply.pojos.UcaAuditWorkStatistics;
import com.uca.apply.service.AuditWorkStatisticsService;
import com.uca.apply.vo.UcaAuditWorkStatisticsVo;
import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.pojos.UmUser;

@Service
public class AuditWorkStatisticsServiceImpl implements AuditWorkStatisticsService {
    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Autowired
    private AuditWorkStatisticsDao auditWorkStatisticsDao;
    @Autowired
    private UmUserDao umUserDao;

    @Override
    public void find(JSONObject object, Page<UcaAuditWorkStatistics> page, String projectName, String sd, String ed) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = auditWorkStatisticsDao.getSession().createCriteria(UcaAuditWorkStatistics.class);
        crit.add(Restrictions.eq("userId", secUtils.getUser().getUserId()));
        if (ParameterUtil.isNotNull(projectName)) {
            crit.add(Restrictions.like("projectName", projectName, MatchMode.ANYWHERE));
        }
        if (null != start && null != end) {
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("createTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("createTime", end));
            }
        }

        int total = crit.list().size();

        crit.addOrder(Order.desc("createTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaAuditWorkStatistics> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            for (UcaAuditWorkStatistics obj : lists) {
                UmUser user = umUserDao.queryById(obj.getUserId());
                if (null != user) {
                    obj.setUserName(user.getUserName());
                }
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaArchivesCatalog>());
        }
    }

    @Override
    public void saveOrUpdate(JSONObject object, UcaAuditWorkStatistics po) {
        // TODO Auto-generated method stub
        if (po.getWorkId() == null) {
            this.save(object, po);
        } else {
            this.update(object, po);
        }
    }

    private void save(JSONObject object, UcaAuditWorkStatistics po) {
        po.setUserId(secUtils.getUser().getUserId());
        po.setCreateTime(new Date());
        auditWorkStatisticsDao.saveOrUpdate(po);
        if (po.getWorkId() > 0) {
            object.put("success", true);
            object.put("msg", "添加成功！");
        } else {
            object.put("success", false);
            object.put("msg", "添加失败！");
        }
    }

    private void update(JSONObject object, UcaAuditWorkStatistics po) {
        UcaAuditWorkStatistics uac = auditWorkStatisticsDao.queryById(po.getWorkId());
        if (null == uac) {
            object.put("success", false);
            object.put("msg", "审核工作量不存在！");
            return;
        }

        uac.setProjectName(po.getProjectName());
        uac.setPicNum(po.getPicNum());
        uac.setPicSize(po.getPicSize());
        uac.setMovMinute(po.getMovMinute());
        uac.setMovSize(po.getMovSize());
        auditWorkStatisticsDao.saveOrUpdate(uac);

        object.put("success", true);
        object.put("msg", "修改成功！");
    }

    @Override
    public void getById(JSONObject object, Integer id) {
        UcaAuditWorkStatistics po = auditWorkStatisticsDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        object.put("success", true);
        object.put("objPo", po);
    }

    @Override
    public void delete(JSONObject object, int catalogId) {
        UcaAuditWorkStatistics po = auditWorkStatisticsDao.queryById(catalogId);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        auditWorkStatisticsDao.delete(po);

        object.put("success", true);
        object.put("msg", "删除成功！");
    }

    @Override
    public void find(JSONObject object, String userId, String sd, String ed) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        Criteria crit = auditWorkStatisticsDao.getSession().createCriteria(UcaAuditWorkStatistics.class);
        if (null != userId && ParameterUtil.isNumber(userId)) {
            crit.add(Restrictions.eq("userId", Integer.parseInt(userId)));
        }
        if (null != start && null != end) {
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if (null != start) {
                crit.add(Restrictions.ge("createTime", start));
            } else if (null != end) {
                crit.add(Restrictions.le("createTime", end));
            }
        }
        List<UcaAuditWorkStatistics> lists = crit.list();

        List<UcaAuditWorkStatisticsVo> retList = new ArrayList<UcaAuditWorkStatisticsVo>();

        Map<Integer, List<UcaAuditWorkStatistics>> tempMap = new HashMap<Integer, List<UcaAuditWorkStatistics>>();
        if (lists != null && lists.size() > 0) {
            for (UcaAuditWorkStatistics obj : lists) {
                UmUser user = umUserDao.queryById(obj.getUserId());
                if (null != user) {
                    obj.setUserName(user.getUserName());
                }
                List<UcaAuditWorkStatistics> tempList = tempMap.get(obj.getUserId());
                if (null != tempList && tempList.size() > 0) {
                    tempList.add(obj);
                    tempMap.put(obj.getUserId(), tempList);
                } else {
                    tempList = new ArrayList<UcaAuditWorkStatistics>();
                    tempList.add(obj);
                    tempMap.put(obj.getUserId(), tempList);
                }
            }
        }
        int allPicNum = 0;
        int allMovMinute = 0;
        if (null != tempMap && tempMap.size() > 0) {
            for (Integer key : tempMap.keySet()) {
                StringBuffer projectName = new StringBuffer();
                String userName = "";
                int picNumTotal = 0;
                double picSizeTotal = 0;
                int movMinuteTotal = 0;
                double movSizeTotal = 0;
                List<UcaAuditWorkStatistics> temp = tempMap.get(key);
                if (null != temp && temp.size() > 0) {
                    for (UcaAuditWorkStatistics ob : temp) {
                        projectName.append(ob.getProjectName()).append("<br />");
                        userName = ob.getUserName();
                        picNumTotal += null == ob.getPicNum() ? 0 : ob.getPicNum();
                        picSizeTotal += null == ob.getPicSize() ? 0 : ob.getPicSize();
                        movMinuteTotal += null == ob.getMovMinute() ? 0 : ob.getMovMinute();
                        movSizeTotal += null == ob.getMovSize() ? 0 : ob.getMovSize();
                    }
                }
                String projectNameTotal = projectName.toString();
                if (projectNameTotal.endsWith(",")) {
                    projectNameTotal = projectNameTotal.substring(0, projectNameTotal.length() - 1);
                }
                UcaAuditWorkStatisticsVo vo = new UcaAuditWorkStatisticsVo();
                vo.setProjectNameTotal(projectNameTotal);
                vo.setUserName(userName);
                vo.setPicNumTotal(picNumTotal);
                vo.setPicSizeTotal(picSizeTotal);
                vo.setMovMinuteTotal(movMinuteTotal);
                vo.setMovSizeTotal(movSizeTotal);
                retList.add(vo);

                allPicNum += picNumTotal;
                allMovMinute += movMinuteTotal;
            }
        }

        object.put("totalCount", lists.size());
        object.put("allPicNum", allPicNum);
        object.put("allMovMinute", allMovMinute);
        object.put("rows", retList);
        object.put("success", true);
        object.put("msg", "成功");
    }

}
