package com.uca.duty.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.duty.dao.DutyDao;
import com.uca.duty.pojos.UcaDuty;
import com.uca.duty.service.DutyService;
import com.uca.utils.BuildFileNameUtil;
import com.uca.utils.DateUtils;
import com.uca.utils.PoiExcelHelper;

@Service
public class DutyServiceImpl implements DutyService {
    @Autowired
    private DutyDao dutyDao;

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    /**
     * 值班表分页查询
     * 
     * @param object
     * @param page
     * @param bgTime
     * @param endTime
     */
    @Override
    public void findDuty(JSONObject object, Page<UcaDuty> page, String bgTime, String endTime) {
        // TODO Auto-generated method stub
        List<Criterion> cnList = new ArrayList<Criterion>(0);
        if (ParameterUtil.isNotBlank(bgTime) && ParameterUtil.isNotBlank(endTime)) {
            cnList.add(Restrictions.ge("createTime", DateUtil.parseTime(bgTime + " 00:00:00")));
            cnList.add(Restrictions.le("createTime", DateUtil.parseTime(endTime + " 23:59:59")));
        }

        ProjectionList proList = Projections.projectionList();// 设置投影集合
        proList.add(Projections.property("t.dutyId").as("dutyId"));
        proList.add(Projections.property("t.dutyDate").as("dutyDate"));
        proList.add(Projections.property("t.dayShiftUser").as("dayShiftUser"));
        proList.add(Projections.property("t.nightShiftUser").as("nightShiftUser"));
        proList.add(Projections.property("t.leader").as("leader"));
        proList.add(Projections.property("t.createTime").as("createTime"));

        page.addOrderBy("dutyDate");
        page.addOrderDir("desc");

        this.dutyDao.findList(cnList, proList, page);

        object.put("rows", page.getListResult());
        object.put("total", page.getTotalRowCount());
    }

    /**
     * 值班列表查询（默认查询当月数据）
     * 
     * @param page
     * @throws ParseException
     */
    @Override
    public void findDutyForUser(JSONObject object, String qDate) throws ParseException {
        StringBuilder sql = new StringBuilder("select * from uca_duty where 1=1 ");
        if (StringUtils.isEmpty(qDate))
            sql.append("and date_format(duty_date,'%Y%m') = date_format(current_date,'%Y%m') ");
        else {
            sql.append("and duty_date like :qDate ");
        }
        sql.append("order by duty_date asc");

        SQLQuery query = this.dutyDao.getSession().createSQLQuery(sql.toString()).addEntity(UcaDuty.class);
        if (StringUtils.isNotEmpty(qDate))
            query.setParameter("qDate", qDate + "%");
        List<UcaDuty> li = query.list();
        // 按周格式化数据
        if (li != null && li.size() > 0) {
            object.put("success", true);
            object.put("map", buildDutyList(li));
        } else {
            object.put("success", false);
            object.put("msg", "选择的记录不存在！");
        }

    }

    private LinkedHashMap<String, List<UcaDuty>> buildDutyList(List<UcaDuty> li) {
        LinkedHashMap<String, List<UcaDuty>> rtnMap = new LinkedHashMap<String, List<UcaDuty>>(0);
        List<UcaDuty> rtnLi = null;
        String cacheKey = "";
        int ind = 0;
        UcaDuty po = null;
        ind++;
        cacheKey += ind + "," + (StringUtils.isNotEmpty(li.get(0).getLeader()) ? (li.get(0).getLeader() + ",") : "");
        rtnLi = new ArrayList<UcaDuty>();
        rtnLi.add(li.get(0));
        rtnMap.put(cacheKey.substring(0, cacheKey.length() - 1), rtnLi);
        for (int i = 1; i < li.size(); i++) {
            po = li.get(i);
            if (2 == DateUtils.getWeek(po.getDutyDate())) {
                rtnMap.put(cacheKey.substring(0, cacheKey.length() - 1), rtnLi);
                ind++;
                cacheKey = "";
                cacheKey += ind + "," + (StringUtils.isNotEmpty(po.getLeader()) ? (po.getLeader() + ",") : "");
                rtnLi = new ArrayList<UcaDuty>();
                rtnLi.add(po);
                if (i == li.size() - 1) {
                    rtnMap.put(cacheKey.substring(0, cacheKey.length() - 1), rtnLi);
                }
            } else if (i == li.size() - 1) {
                if (StringUtils.isNotEmpty(po.getLeader()) && cacheKey.indexOf("," + po.getLeader() + ",") == -1) {
                    cacheKey += po.getLeader() + ",";
                }
                rtnLi.add(po);
                rtnMap.put(cacheKey.substring(0, cacheKey.length() - 1), rtnLi);
            } else {
                if (StringUtils.isNotEmpty(po.getLeader()) && cacheKey.indexOf("," + po.getLeader() + ",") == -1) {
                    cacheKey += po.getLeader() + ",";
                }
                rtnLi.add(po);
            }
        }
        return rtnMap;
    }

    /**
     * 保存和更新值班信息
     * 
     * @param object
     * @param po
     */
    @Override
    public void saveOrUpdateDuty(JSONObject object, UcaDuty po) {
        // TODO Auto-generated method stub
        if (po.getDutyId() == null) {
            this.saveDuty(object, po);
        } else {
            this.updateDuty(object, po);
        }
    }

    private void saveDuty(JSONObject object, UcaDuty po) {
        po.setWeek(DateUtils.getWeekStr(po.getDutyDate()));
        Date time = new Date();
        po.setCreateTime(time);
        BaseUser user = secUtils.getUser();
        po.setCreateUserId(user.getUserId());
        po.setUpdateTime(time);
        this.dutyDao.save(po);
        object.put("msg", "值班信息保存成功！");
        object.put("success", true);
    }

    private void updateDuty(JSONObject object, UcaDuty po) {
        UcaDuty uPo = this.dutyDao.queryById(po.getDutyId());
        if (uPo != null) {
            uPo.setDayShiftUser(po.getDayShiftUser());
            uPo.setDutyDate(po.getDutyDate());
            uPo.setLeader(po.getLeader());
            uPo.setLunarCalendar(po.getLunarCalendar());
            uPo.setNightShiftUser(po.getNightShiftUser());
            uPo.setWeek(DateUtils.getWeekStr(po.getDutyDate()));

            BaseUser user = secUtils.getUser();
            Date updateTime = new Date();
            uPo.setUpdateUserId(user.getUserId());
            uPo.setUpdateTime(updateTime);

            this.dutyDao.update(uPo);
            object.put("success", true);
            object.put("msg", "值班信息保存成功！");
        } else {
            object.put("success", false);
            object.put("msg", "选择的记录不存在！");
        }
    }

    /**
     * 根据ID获取值班信息
     * 
     * @param object
     * @param id
     */
    @Override
    public void getByDutyId(JSONObject object, Integer id) {
        // TODO Auto-generated method stub
        UcaDuty po = new UcaDuty();
        if (id != null) {
            po = this.dutyDao.queryById(id);
        }

        object.put("success", true);
        object.put("objPo", po);
    }

    /**
     * 根据ID删除值班信息
     * 
     * @param object
     * @param catalogId
     */
    @Override
    public void deleteDutyById(JSONObject object, int dId) {
        // TODO Auto-generated method stub
        UcaDuty po = this.dutyDao.queryById(dId);
        if (po == null) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
        } else {
            this.dutyDao.delete(po);
            object.put("success", true);
            object.put("msg", "删除成功！");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @Override
    public void importDuty(JSONObject object, MultipartFile file) throws Exception {
        // TODO Auto-generated method stub
        File fileImport = null;
        String fileUrl = "";
        String fileName = "";
        if (!file.isEmpty()) {
            fileName = file.getOriginalFilename();// 原始文件名称
            try {
                fileUrl = BuildFileNameUtil.getNewFileUrl(fileName);
                fileImport = new File(fileUrl);
                FileCopyUtils.copy(file.getBytes(), fileImport);
            } catch (IOException e) {
                throw e;
            }
        }

        List<ArrayList<String>> excelData = new ArrayList<ArrayList<String>>();

        if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xls")
                || file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1).equals("xlsx")) {// 转换csv到excelData对象sh
            PoiExcelHelper peh = new PoiExcelHelper();
            excelData = peh.read(fileImport.getAbsolutePath(), 0);
        } else {
            object.put("success", false);
            object.put("msg", "模板必须为Excel文件");
            return;
        }

        ArrayList<String> strsTitle = excelData.get(0);
        if (!"值班日期".equals(strsTitle.get(0)) || !"白班值班人员".equals(strsTitle.get(1))
                || !"夜班值班人员".equals(strsTitle.get(2)) || !"带班领导".equals(strsTitle.get(3))) {
            object.put("success", false);
            object.put("msg", "模板格式错误！");
            return;
        }

        excelData.remove(0);// 去掉标题
        BaseUser user = secUtils.getUser();
        try {
            List<UcaDuty> list = new ArrayList<UcaDuty>();
            for (ArrayList<String> strs : excelData) {
                String dutyDate = strs.get(0);
                String dayShiftUser = strs.get(1);
                String nightShiftUser = strs.get(2);
                String leader = strs.get(3);

                if (ParameterUtil.isNull(dutyDate)) {
                    continue;
                }

                Date time = new Date();

                UcaDuty po = new UcaDuty();
                po.setDutyDate(dutyDate != null ? DateUtil.parseDate(dutyDate) : null);
                po.setWeek(DateUtils.getWeekStr(po.getDutyDate()));
                po.setDayShiftUser(dayShiftUser);
                po.setNightShiftUser(nightShiftUser);
                po.setLeader(leader);
                po.setCreateUserId(user.getUserId());
                po.setUpdateUserId(user.getUserId());
                po.setCreateTime(time);
                po.setUpdateTime(time);

                list.add(po);
            }
            this.dutyDao.saveOrUpdateAll(list);
            object.put("success", true);
            object.put("msg", "批量导入数据成功！");
        } catch (Exception e) {
            throw e;
        }
    }
}
