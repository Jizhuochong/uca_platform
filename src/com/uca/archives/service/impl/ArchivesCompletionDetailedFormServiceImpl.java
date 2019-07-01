package com.uca.archives.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.dao.ArchivesCompletionDetailedFormDao;
import com.uca.archives.pojos.ArchivesCompletionDetailedForm;
import com.uca.archives.service.ArchivesCompletionDetailedFormService;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.pojos.UmUser;

@Service
public class ArchivesCompletionDetailedFormServiceImpl implements ArchivesCompletionDetailedFormService {

    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Autowired
    private ArchivesCompletionDetailedFormDao archivesCompletionDetailedFormDao;
    @Autowired
    private UmUserDao umUserDao;

    @Override
    public void find(JSONObject object, Page<ArchivesCompletionDetailedForm> page, int registerId) {
        Criteria crit = archivesCompletionDetailedFormDao.getSession().createCriteria(
                ArchivesCompletionDetailedForm.class);
        crit.add(Restrictions.eq("registerId", registerId));

        int total = crit.list().size();

        crit.addOrder(Order.desc("createTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<ArchivesCompletionDetailedForm> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            for (ArchivesCompletionDetailedForm obj : lists) {
                UmUser user = umUserDao.queryById(obj.getCreateUserId());
                if (null != user) {
                    obj.setCreateUserName(user.getUserName());
                }
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<ArchivesCompletionDetailedForm>());
        }
    }

    @Override
    public void saveOrUpdate(JSONObject object, ArchivesCompletionDetailedForm po) {
        if (po.getId() == null) {
            this.save(object, po);
        } else {
            this.update(object, po);
        }
    }

    private void save(JSONObject object, ArchivesCompletionDetailedForm po) {
        po.setCreateUserId(secUtils.getUser().getUserId());
        po.setCreateTime(new Date());
        archivesCompletionDetailedFormDao.saveOrUpdate(po);
        if (po.getId() > 0) {
            object.put("success", true);
            object.put("msg", "添加成功！");
        } else {
            object.put("success", false);
            object.put("msg", "添加失败！");
        }
    }

    private void update(JSONObject object, ArchivesCompletionDetailedForm po) {
        ArchivesCompletionDetailedForm uac = archivesCompletionDetailedFormDao.queryById(po.getId());
        if (null == uac) {
            object.put("success", false);
            object.put("msg", "竣工档案登记表不存在！");
            return;
        }

        uac.setProjectPurpose(po.getProjectPurpose());
        uac.setCategoryStructure(po.getCategoryStructure());
        uac.setFloors(po.getFloors());
        uac.setCoversArea(po.getCoversArea());
        uac.setConstructionArea(po.getConstructionArea());
        uac.setBuildings(po.getBuildings());
        uac.setTotalInvestment(po.getTotalInvestment());
        uac.setProjectContent(po.getProjectContent());
        uac.setProjectQuantity(po.getProjectQuantity());
        uac.setDiameterSection(po.getDiameterSection());
        uac.setDiameterLength(po.getDiameterLength());

        archivesCompletionDetailedFormDao.saveOrUpdate(uac);

        object.put("success", true);
        object.put("msg", "修改成功！");
    }

    @Override
    public void getById(JSONObject object, Integer id) {
        ArchivesCompletionDetailedForm po = archivesCompletionDetailedFormDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }
        object.put("success", true);
        object.put("objPo", po);
    }

    @Override
    public void delete(JSONObject object, int id) {
        ArchivesCompletionDetailedForm po = archivesCompletionDetailedFormDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        archivesCompletionDetailedFormDao.delete(po);

        object.put("success", true);
        object.put("msg", "删除成功！");
    }

    @Override
    public List<ArchivesCompletionDetailedForm> findByRegisterId(int registerId) {
        Criteria crit = archivesCompletionDetailedFormDao.getSession().createCriteria(
                ArchivesCompletionDetailedForm.class);
        crit.add(Restrictions.eq("registerId", registerId));
        crit.addOrder(Order.asc("createTime"));
        List<ArchivesCompletionDetailedForm> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            return lists;
        }
        return null;
    }

}
