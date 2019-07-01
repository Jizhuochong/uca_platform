package com.uca.ucasecurity.service.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.capinfo.auth.dao.ResourceDao;
import cn.com.capinfo.auth.pojos.Resource;

@Service
public class UcaResourceServiceImpl {

	@Autowired
    private ResourceDao resourceDao;
	
	/**
     * 按照父节点ID查询树
     * @param parentId
     * */
    public List<Resource> findIndexMenu(String rcodes, int resPid) {
        String sql = "select distinct res.* from UM_RESOURCE res " +
        		"inner join UM_ROLE_RESOURCE urr on urr.resource_id = res.resource_id " +
        		"inner join UM_ROLE r on urr.role_id=r.role_id " +
        		"where r.role_code in ("+rcodes+") and res.resource_status=1 and res.resource_showflag=1 " +
        		"and res.resource_parent_id=:resPid order by res.resource_sort asc";
        SQLQuery query = resourceDao.getSession().createSQLQuery(sql).addEntity(Resource.class);
        query.setParameter("resPid", resPid);
        List<Resource> resList = query.list();
        if(null != resList && resList.size() > 0) {
            for(Resource res : resList){
                List<Resource> childrenList = this.findIndexMenu(rcodes, res.getResourceId());
                res.setChildrenList(childrenList);
            }
        }
        return resList;
    }
}