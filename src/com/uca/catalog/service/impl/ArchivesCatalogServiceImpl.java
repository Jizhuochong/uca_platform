package com.uca.catalog.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.pojos.UcaArchives;
import com.uca.archives.service.ArchivesService;
import com.uca.catalog.dao.ArchivesCatalogDao;
import com.uca.catalog.pojos.UcaArchivesCatalog;
import com.uca.catalog.service.ArchivesCatalogService;

@Service
public class ArchivesCatalogServiceImpl implements ArchivesCatalogService{
	private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Autowired
	private ArchivesCatalogDao archivesCatalogDao;
	
	@Autowired
	private ArchivesService archivesService;
	
	/**
	 * 档案目录分页查询
	 * @param object
	 * @param page 
	 */
	@Override
	public void find(JSONObject object, Page<UcaArchivesCatalog> page, 
			String archivesNum, String projectName, String devOrg, String projectAddress, 
			String planPerNum, String sd, String ed) {
		// TODO Auto-generated method stub
		Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        
        Criteria crit = archivesCatalogDao.getSession().createCriteria(UcaArchivesCatalog.class);
        crit.add(Restrictions.eq("createUserId", secUtils.getUser().getUserId()));
//        if(ParameterUtil.isNotNull(archivesId) && ParameterUtil.isNumber(archivesId)) {
//        	crit.createAlias("ucaArchives", "a");
//            crit.add(Restrictions.eq("a.archivesId", Integer.parseInt(archivesId)));
//        }
       /* if(ParameterUtil.isNotNull(archivesNum)) {
            crit.add(Restrictions.eq("archivesNum", archivesNum));
        }*/
        if(ParameterUtil.isNotNull(archivesNum)) {
        	crit.add(Restrictions.like("archivesNum", archivesNum, MatchMode.ANYWHERE));
        }
        if(ParameterUtil.isNotNull(projectName)) {
            crit.add(Restrictions.like("projectName", projectName, MatchMode.ANYWHERE));
        }
        if(ParameterUtil.isNotNull(devOrg)) {
            crit.add(Restrictions.like("devOrg", devOrg, MatchMode.ANYWHERE));
        }
        if(ParameterUtil.isNotNull(projectAddress)) {
            crit.add(Restrictions.like("projectAddress", projectAddress, MatchMode.ANYWHERE));
        }
        if(ParameterUtil.isNotNull(planPerNum)) {
            crit.add(Restrictions.like("planPerNum", planPerNum, MatchMode.ANYWHERE));
        }
        if(null != start && null != end){
            crit.add(Restrictions.between("createTime", start, end));
        } else {
            if(null != start){
                crit.add(Restrictions.ge("createTime", start));
            } else if(null != end){
                crit.add(Restrictions.le("createTime", end));
            }
        }
        
        int total = crit.list().size();
        
        crit.addOrder(Order.desc("createTime"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaArchivesCatalog> lists = crit.list();
        
        if(lists != null && lists.size() > 0) {
            for(UcaArchivesCatalog catalog : lists) {
            	UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(archivesNum);
            	if(null != ucaArchives) {
            		catalog.setArchivesId(ucaArchives.getArchivesId());
            		catalog.setOrderNum(ucaArchives.getOrderNum());
            	}
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaArchivesCatalog>());
        }
	}
	
	/**
	 * 档案目录分页查询无需登录
	 * @param projectName 工程名称
	 * @param page
	 * @throws ParseException 
	 */
	@Override
	public void findCatalogForUser(String projectName, Page<UcaArchivesCatalog> page) throws ParseException {
		// TODO Auto-generated method stub
		List<Criterion> cnList = new ArrayList<Criterion>(0);
		if(StringUtils.isNotEmpty(projectName)){
			cnList.add(Restrictions.or(Restrictions.like("projectName", "%"+projectName+"%"),Restrictions.like("archivesNum", "%"+projectName+"%")));
		}
		/*
		//查询最近3个月的记录
		String endTime = DateUtil.getStrDate_24();
		cnList.add(Restrictions.between("uploadTime", DateUtil.otherDate(endTime, "yyyy-MM-dd HH:mm:ss", -90), DateUtil.parseTime(endTime)));*/
		ProjectionList proList = Projections.projectionList();//设置投影集合  
	    proList.add(Projections.property("t.archivesNum").as("archivesNum"));
	    proList.add(Projections.property("t.projectName").as("projectName"));
	    proList.add(Projections.property("t.devOrg").as("devOrg"));
	    proList.add(Projections.property("t.projectAddress").as("projectAddress"));
	    proList.add(Projections.property("t.planPerNum").as("planPerNum"));
	    proList.add(Projections.property("t.constructionUnit").as("constructionUnit"));
		
		page.addOrderBy("updateTime");
        page.addOrderDir("desc");
        
        List<UcaArchivesCatalog> findList = this.archivesCatalogDao.findList(cnList, proList, page);
        System.out.println();
	}
	
	/**
	 * 保存和更新档案目录
	 * @param object
	 * @param po
	 */
	@Override
	public void saveOrUpdate(JSONObject object, UcaArchivesCatalog po) {
		// TODO Auto-generated method stub
		if(po.getCatalogId() == null){
			this.saveArchivesCatalog(object, po);
		}else{
			this.updateArchivesCatalog(object, po);
		}
	}
	
	private void saveArchivesCatalog(JSONObject object, UcaArchivesCatalog po) {
/*		UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(po.getArchivesNum());
		if(null == ucaArchives) {
			object.put("success", false);
			object.put("msg", "填写档号不存在！");
			return;
		}*/
		po.setCreateUserId(secUtils.getUser().getUserId());
		po.setUpdateUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		po.setCreateTime(time);
		po.setUpdateTime(time);
		archivesCatalogDao.saveOrUpdate(po);
		
		if(po.getCatalogId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}
	
	private void updateArchivesCatalog(JSONObject object, UcaArchivesCatalog po) {
		UcaArchivesCatalog uac = archivesCatalogDao.queryById(po.getCatalogId());
		if(null == uac) {
			object.put("success", false);
			object.put("msg", "住宅类项目级档案目录不存在！");
			return;
		}
		/*UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(po.getArchivesNum());
		if(null == ucaArchives) {
			object.put("success", false);
			object.put("msg", "填写档号不存在！");
			return;
		}*/
		
		uac.setArchivesNum(po.getArchivesNum());
		uac.setProjectName(po.getProjectName());
		uac.setDevOrg(po.getDevOrg());
		uac.setProjectAddress(po.getProjectAddress());
		uac.setPlanPerNum(po.getPlanPerNum());
		uac.setUpdateUserId(secUtils.getUser().getUserId());
		uac.setUpdateTime(new Date());
		uac.setDesignUnit(po.getDesignUnit());
		uac.setConstructionUnit(po.getConstructionUnit());
		archivesCatalogDao.saveOrUpdate(uac);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
	}

	/**
	 * 根据ID获取档案目录
	 * @param object
	 * @param id
	 */
	@Override
	public void getById(JSONObject object, Integer id) {
		// TODO Auto-generated method stub
		UcaArchivesCatalog po = archivesCatalogDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		if(null != po.getCreateUserId() && po.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配！");
			return;
		}
		
		UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(po.getArchivesNum());
		
		if(null != ucaArchives) {
			po.setArchivesId(ucaArchives.getArchivesId());
			po.setOrderNum(ucaArchives.getOrderNum());
    	}
		
		object.put("success", true);
		object.put("objPo", po);
	}
	
	@Override
	public void delete(JSONObject object, int catalogId) {
		// TODO Auto-generated method stub
		UcaArchivesCatalog po = archivesCatalogDao.queryById(catalogId);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		archivesCatalogDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Override
	public void saveCatalogs(List<UcaArchivesCatalog> list) {
		// TODO Auto-generated method stub
		Session session = archivesCatalogDao.getSession();
	    for (int i = 0; i < list.size(); i++) {  
	    	UcaArchivesCatalog catalog = list.get(i);
	        session.save(catalog);
            // 批插入的对象立即写入数据库并释放内存  
            if (i % 1000 == 0) {  
                session.flush();  
                session.clear();  
            }  
        }
	}

}
