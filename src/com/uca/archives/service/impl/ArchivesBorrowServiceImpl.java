package com.uca.archives.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.archives.dao.ArchivesBorrowDao;
import com.uca.archives.pojos.UcaArchivesBorrow;
import com.uca.archives.service.ArchivesBorrowService;

@Service
public class ArchivesBorrowServiceImpl implements ArchivesBorrowService{

	@Autowired
	private ArchivesBorrowDao archivesBorrowDao;

    private SecurityIdentifyUtils secUtils = new SecurityUtils();
	
	@Override
	public void findArchivesSchedule(String num, String person,
			Page<UcaArchivesBorrow> page,JSONObject object) throws ParseException {
		List<Criterion> cnList = new ArrayList<Criterion>(0);
        if (StringUtils.isNotEmpty(num))
            cnList.add(Restrictions.like("num", "%" + num + "%"));
        if (StringUtils.isNotEmpty(person))
        	cnList.add(Restrictions.like("person", "%" + person + "%"));
        

        Criteria crit = archivesBorrowDao.getSession().createCriteria(UcaArchivesBorrow.class);
        crit.add(Restrictions.eq("userId", secUtils.getUser().getUserId()));
        if(ParameterUtil.isNotNull(num)) {
        	crit.add(Restrictions.like("num", num, MatchMode.ANYWHERE));
        }
        if(ParameterUtil.isNotNull(person)) {
            crit.add(Restrictions.like("person", person, MatchMode.ANYWHERE));
        }
        
        int total = crit.list().size();
        
        crit.addOrder(Order.desc("num"));
        crit.setFirstResult(page.getPageNo());
        crit.setMaxResults(page.getPageSize());
        List<UcaArchivesBorrow> lists = crit.list();
        if(lists != null && lists.size() > 0) {
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UcaArchivesBorrow>());
        }

//        this.archivesBorrowDao.findList(cnList, proList, page);
	}

	@Override
	public void saveOrUpdate(JSONObject object, UcaArchivesBorrow ab) {
		if(ab.getId() == null){
			this.saveArchivesCatalog(object, ab);
		}else{
			this.updateArchivesCatalog(object, ab);
		}
	}

	private void updateArchivesCatalog(JSONObject object, UcaArchivesBorrow ab) {
		
		UcaArchivesBorrow uab = archivesBorrowDao.queryById(ab.getId());
		if(null == uab) {
			object.put("success", false);
			object.put("msg", "借阅单不存在！");
			return;
		}
		/*UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(ab.getArchivesNum());
		if(null == ucaArchives) {
			object.put("success", false);
			object.put("msg", "填写档号不存在！");
			return;
		}*/
		uab.setNum(ab.getNum());
		uab.setPerson(ab.getPerson());
		archivesBorrowDao.saveOrUpdate(uab);
		
		object.put("success", true);
        object.put("msg", "修改成功！");
		
	}

	private void saveArchivesCatalog(JSONObject object, UcaArchivesBorrow ab) {
//		BaseUser user = secUtils.getUser();
//		SecurityContext sc = SecurityContextHolder.getContext();
//	    Authentication auth = sc.getAuthentication();
//	    BaseUser user = (BaseUser)auth.getPrincipal();
//	    int userId = user.getUserId();
//		ab.setUserId(userId);
		ab.setUserId(secUtils.getUser().getUserId());
		Date time = new Date();
		ab.setCreateDate(new SimpleDateFormat().format(time));
//		ab.setUpdateTime(time);
		archivesBorrowDao.saveOrUpdate(ab);
		
		if(ab.getId() > 0) {
			object.put("success", true);
	        object.put("msg", "添加成功！");
		} else {
			object.put("success", false);
	        object.put("msg", "添加失败！");
		}
	}

	@Override
	public void delete(JSONObject object, int catalogId) {
		UcaArchivesBorrow po = archivesBorrowDao.queryById(catalogId);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		
		archivesBorrowDao.delete(po);
		
		object.put("success", true);
        object.put("msg", "删除成功！");
	}

	@Override
	public void getById(JSONObject object, int id) {
		UcaArchivesBorrow po = archivesBorrowDao.queryById(id);
		if(null == po) {
			object.put("success", false);
			object.put("msg", "选择的数据记录不存在！");
			return;
		}
		/*if(null != po.getCreateUserId() && po.getCreateUserId().intValue() != secUtils.getUser().getUserId()) {
			object.put("success", false);
			object.put("msg", "当前用户与选择的数据记录创建人不匹配！");
			return;
		}
		
		UcaArchives ucaArchives = archivesService.getArchivesByArchivesNum(po.getArchivesNum());
		
		if(null != ucaArchives) {
			po.setArchivesId(ucaArchives.getArchivesId());
			po.setOrderNum(ucaArchives.getOrderNum());
    	}
    	*/
		
		object.put("success", true);
		object.put("objPo", po);
	}

}
