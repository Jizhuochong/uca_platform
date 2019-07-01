package com.uca.ucasecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.ucasecurity.dao.UmOrgDao;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.service.UmOrgService;

/**
 * 机构表service实现
 * 
 * @author mamm 2014年12月2日
 */
@Service
public class UmOrgServiceImpl implements UmOrgService {
    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    @Autowired
    private UmOrgDao umOrgDao;

    @Autowired
    private UmUserDao umUserDao;

    /**
     * 机构保存
     * 
     * @author mamm 2014年12月2日
     * @param po
     *            机构对象
     */
    @Override
    public void save(UmOrg po) {
        // TODO Auto-generated method stub
        this.umOrgDao.saveOrUpdate(po);
    }

    /**
     * 根据id查询机构
     * 
     * @author mamm 2014年12月2日
     * @param orgId
     *            机构id
     * @return 机构对象
     */
    @Override
    public UmOrg getUmOrgById(Integer orgId) {
        return this.umOrgDao.queryById(orgId);
    }

    /**
     * @author asp_blue 2015年3月2日12:29:42 机构树
     */
    @Transactional
    @Override
    public List<UmOrg> tree(Integer parentOrgId, String type) {
        List<UmOrg> orgList = findListByParent(parentOrgId, type);
        if (null != orgList && orgList.size() > 0) {
            for (UmOrg org : orgList) {
                List<UmOrg> childrenList = this.tree(org.getOrgId(), type);
                org.setChildrenList(childrenList);// 子节点
            }
        }
        return orgList;
    }

    /**
     * @author asp_blue 2015年3月2日12:29:42 机构树
     */
    @Transactional
    @Override
    public List<UmOrg> tree(Integer parentOrgId, Integer type) {
        List<UmOrg> orgList = findListByParent(parentOrgId, type);
        if (null != orgList && orgList.size() > 0) {
            for (UmOrg org : orgList) {
                List<UmOrg> childrenList = this.tree(org.getOrgId(), type);
                org.setChildrenList(childrenList);// 子节点
            }
        }
        return orgList;
    }

    /**
     * @author asp_blue 2015年3月2日12:29:42 机构树
     */
    @Transactional
    @Override
    public List<UmOrg> tree(Integer parentOrgId) {
        List<UmOrg> orgList = findListByParent(parentOrgId);
        if (null != orgList && orgList.size() > 0) {
            for (UmOrg org : orgList) {
                List<UmOrg> childrenList = this.tree(org.getOrgId());
                org.setChildrenList(childrenList);// 子节点
            }
        }
        return orgList;
    }

    private List<UmOrg> findListByParent(Integer parentOrgId) {
        String sql = "select * from um_org where parent_org_id =:parentOrgId order by sort";
        SQLQuery query = this.umOrgDao.getSession().createSQLQuery(sql).addEntity(UmOrg.class);
        query.setParameter("parentOrgId", parentOrgId);
        return query.list();
    }

    /**
     * 根据父ID和机构类型查询
     * 
     * @param parentOrgId
     *            父机构ID
     * @param type
     *            机构类型
     * @return
     */
    private List<UmOrg> findListByParent(Integer parentOrgId, String type) {
        String sql = null;
        // String sql =
        // "select * from um_org where parent_org_id =:parentOrgId and type=:type order by sort";
        if ("1".equals(type)) {
            sql = "select * from um_org where parent_org_id =:parentOrgId and org_name = '工程室' order by sort";
        } else if ("2".equals(type)) {
            sql = "select * from um_org where parent_org_id =:parentOrgId and org_name = '声像室' order by sort";
        } else {
            sql = "select * from um_org where parent_org_id =:parentOrgId order by sort";
        }

        SQLQuery query = this.umOrgDao.getSession().createSQLQuery(sql).addEntity(UmOrg.class);
        query.setParameter("parentOrgId", parentOrgId);
        // query.setParameter("type", type);
        return query.list();
    }

    private List<UmOrg> findListByParent(Integer parentOrgId, Integer type) {
        // String sql =
        // "select * from um_org where parent_org_id =:parentOrgId and type=:type order by sort";
        String sql = "select * from um_org where parent_org_id =:parentOrgId order by sort";

        SQLQuery query = this.umOrgDao.getSession().createSQLQuery(sql).addEntity(UmOrg.class);
        query.setParameter("parentOrgId", parentOrgId);
        // query.setParameter("type", type);
        return query.list();
    }

    @Override
    public void find(JSONObject object, Page<UmOrg> page, String orgCode, String sd, String ed) {
        // TODO Auto-generated method stub
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }

        Criteria crit = umOrgDao.getSession().createCriteria(UmOrg.class);
        if (ParameterUtil.isNotNull(orgCode)) {
            crit.add(Restrictions.like("orgCode", orgCode, MatchMode.ANYWHERE));
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
        List<UmOrg> lists = crit.list();

        if (lists != null && lists.size() > 0) {
            for (UmOrg po : lists) {
                po.setChildrenList(null);
            }
            object.put("total", total);
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UmOrg>());
        }
    }

    @Override
    public void saveOrUpdate(JSONObject object, UmOrg po) {
        // TODO Auto-generated method stub
        if (po.getOrgId() == null) {
            this.save(object, po);
        } else {
            this.update(object, po);
        }
    }

    private void save(JSONObject object, UmOrg po) {
        po.setCreateUserId(secUtils.getUser().getUserId());
        po.setUpdateUserId(secUtils.getUser().getUserId());
        Date time = new Date();
        po.setCreateTime(time);
        po.setUpdateTime(time);
        umOrgDao.saveOrUpdate(po);

        if (po.getOrgId() > 0) {
            object.put("success", true);
            object.put("msg", "添加成功！");
        } else {
            object.put("success", false);
            object.put("msg", "添加失败！");
        }
    }

    private void update(JSONObject object, UmOrg po) {
        UmOrg uorg = umOrgDao.queryById(po.getOrgId());
        if (null == uorg) {
            object.put("success", false);
            object.put("msg", "机构不存在！");
            return;
        }
        uorg.setOrgCode(po.getOrgCode());
        uorg.setOrgName(po.getOrgName());
        uorg.setDescription(po.getDescription());
        uorg.setSort(po.getSort());
        uorg.setType(po.getType());
        uorg.setUpdateTime(new Date());
        uorg.setUpdateUserId(secUtils.getUser().getUserId());
        umOrgDao.saveOrUpdate(uorg);

        object.put("success", true);
        object.put("msg", "修改成功！");
    }

    @Override
    public void getById(JSONObject object, int id) {
        // TODO Auto-generated method stub
        UmOrg po = umOrgDao.queryById(id);
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
        // TODO Auto-generated method stub
        UmOrg po = umOrgDao.queryById(id);
        if (null == po) {
            object.put("success", false);
            object.put("msg", "选择的数据记录不存在！");
            return;
        }

        umOrgDao.delete(po);

        object.put("success", true);
        object.put("msg", "删除成功！");
    }

    @Override
    public List<UmOrg> findDatas() {
        // TODO Auto-generated method stub
        List<Criterion> criterions = new ArrayList<Criterion>();
        return umOrgDao.findList(criterions, "sort", "asc");
    }

    private String userSql = "select user_id,user_name from um_user where org_id =:orgId";

    @Transactional
    @Override
    public List<UmOrg> treeForUser(UmOrg po, Integer parentOrgId, Integer type) {
        List<UmOrg> orgList = findListByParent(parentOrgId, type);
        if (null != orgList && orgList.size() > 0) {
            for (UmOrg org : orgList) {
                List<UmOrg> childrenList = this.treeForUser(org, org.getOrgId(), type);
                if (childrenList != null && childrenList.size() > 0)
                    org.setChildrenList(childrenList);// 子节点
            }
        } else {// 查询机构下的用户，并将用户信息灌入机构节点中
            if (po != null) {
                po.setChildrenList(findUserByOrgId(parentOrgId));
            }else{
            	orgList.addAll(findUserByOrgId(parentOrgId));
            }
        }
        return orgList;
    }

    private List<UmOrg> findUserByOrgId(Integer orgId) {
        SQLQuery query = this.umUserDao.createSQLQuery(userSql);
        query.setParameter("orgId", orgId);
        List<Object[]> li = query.list();
        List<UmOrg> rtnList = new ArrayList<UmOrg>(0);
        UmOrg po = null;
        if (li.size() > 0) {
            for (Object[] obj : li) {
                po = new UmOrg();
                po.setOrgId(Integer.valueOf(obj[0].toString()));
                po.setOrgName(obj[1].toString());
                po.setParentOrgId(orgId);
                po.setType(UcaConstants.ORG_TYPE_USER);
                rtnList.add(po);
            }

        }
        return rtnList;
    }

    @Override
    public List<UmOrg> getUmOrgTree(int pid) {
        String sql = "select * from um_org where parent_org_id =:parentOrgId order by sort";
        SQLQuery query = this.umOrgDao.getSession().createSQLQuery(sql).addEntity(UmOrg.class);
        query.setParameter("parentOrgId", pid);
        return query.list();
    }
}
