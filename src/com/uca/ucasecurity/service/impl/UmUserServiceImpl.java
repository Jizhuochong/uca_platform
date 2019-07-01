package com.uca.ucasecurity.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.auth.pojos.Role;
import cn.com.capinfo.core.pojos.BaseRole;
import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.service.RoleService;
import cn.com.capinfo.core.utils.DateUtil;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;
import cn.com.capinfo.core.utils.SystemProperties;

import com.alibaba.fastjson.JSONObject;
import com.uca.constants.UcaConstants;
import com.uca.ucasecurity.dao.UmOrgDao;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.ucasecurity.service.UmUserExpandService;
import com.uca.ucasecurity.service.UmUserService;
import com.uca.ucasecurity.util.UserStatusEnums;

@Service
public class UmUserServiceImpl implements UmUserService {

    @Autowired
    private UmUserDao umUserDao;
    
    @Autowired
    private UmOrgDao umOrgDao;
    
    @Autowired
    private UmUserExpandService uueService;
    
    @Autowired
	private RoleService roleService;
    
    
    
    public void findDatas(JSONObject object, Page<? extends BaseUser> page, 
            String uaccount, String uname, String orgId, String sd, String ed) {
        Date start = null;
        Date end = null;
        if (ParameterUtil.isNotBlank(sd) && ParameterUtil.isDate(sd)) {
            start = DateUtil.parseTime(sd + " 00:00:00");
        }
        if (ParameterUtil.isNotBlank(ed) && ParameterUtil.isDate(ed)) {
            end = DateUtil.parseTime(ed + " 23:59:59");
        }
        List<Criterion> cnList = new ArrayList<Criterion>();
        cnList.add(Restrictions.like("userAccount", uaccount != null ? uaccount : "", MatchMode.ANYWHERE));
        cnList.add(Restrictions.like("userName", uname != null ? uname : "", MatchMode.ANYWHERE));
        if(ParameterUtil.isNotNull(orgId) && ParameterUtil.isNumber(orgId)) {
        	cnList.add(Restrictions.eq("orgId", Integer.parseInt(orgId)));
        }
        if(null != start && null != end){
            cnList.add(Restrictions.between("userRegistTime", start, end));
        } else {
            if(null != start){
                cnList.add(Restrictions.ge("userRegistTime", start));
            } else if(null != end){
                cnList.add(Restrictions.le("userRegistTime", end));
            }
        }
//        cnList.add(Restrictions.eq("userStatus", UserStatusEnums.USER_STATUS_USE.getKey()));
        List<UmUser> lists = umUserDao.findList(cnList, (Page<UmUser>)page);
        if(lists != null && lists.size() > 0) {
            //去除级联操作
            for(UmUser umUser : lists){
            	umUser.setUmRoles(null);
                
                String roles = this.findRoleByUser(umUser.getUserId());
                if(ParameterUtil.isNotEmpty(roles) && roles.length() > 0) {
                	umUser.setRoles(roles.substring(0, roles.length()-1));
                }
                
                List<Role> rolesList = this.getRoleByUserId(umUser.getUserId());
                StringBuffer sb = new StringBuffer();
                for(Role r : rolesList){
                    sb.append(r.getRoleName()).append(",");
                }
                String role = sb.toString();
                if(role.endsWith(",")) {
                    role = role.substring(0, role.length() - 1);
                }
                umUser.setRoleName(role);
                
                UmOrg org = umOrgDao.queryById(umUser.getOrgId());
                if(null != org) {
                	umUser.setOrgName(org.getOrgName());
                }
            }
            object.put("total", page.getTotalRowCount());
            object.put("rows", lists);
        } else {
            object.put("total", 0);
            object.put("rows", new ArrayList<UmUser>());
        }
    }
    
    @Override
    public List<UmUser> findDatas() {
        List<Criterion> cnList = new ArrayList<Criterion>();
        cnList.add(Restrictions.eq("userStatus", UserStatusEnums.USER_STATUS_USE.getKey()));
        List<UmUser> lists = umUserDao.findList(cnList);
        return lists;
    }
    
    public void save(JSONObject object, BaseUser obj, String[] roleIds) {
        Set<Role> roleSet = (Set<Role>) obj.getUmRoles();
        if (roleIds != null) {
            Role role = null;
            for (String roleId : roleIds) {
                role = new Role();
                role.setRoleId(Integer.parseInt(roleId));
                roleSet.add(role);
            }
        }
        umUserDao.saveOrUpdate((UmUser)obj);
        
        //保存用户扩展信息
        UmUserExpand uue = new UmUserExpand();
        uue.setUserId(obj.getUserId());
        
        //判断用户类型
        UmOrg org = umOrgDao.queryById(((UmUser)obj).getOrgId());
        uue.setType(org.getType());//与机构类型保持一致
        uue.setOrgId(org.getOrgId());
        
        this.uueService.save(uue);
        
        if(obj.getUserId() > 0) {
            object.put("success", true);
        } else {
            object.put("success", false);
            object.put("msg", "保存失败！");
        }
    }
    
    public void save(JSONObject object, BaseUser obj) {
    	//设置固定的角色信息（建设单位用户）
		Set<BaseRole> umRoles = new HashSet<BaseRole>();
		List<String> queryList = new ArrayList<String>();
		queryList.add(UcaConstants.UM_ROLE_CODE_USER);
		List<? extends BaseRole> roleList = this.roleService.findByCode(queryList);
		umRoles.add(roleList.get(0));
		obj.setUmRoles(umRoles);
		
		//注册用户默认挂载到"建设单位"机构下
		int orgId = Integer.parseInt(SystemProperties.getProperty("org.default.id"));
		UmUser user = (UmUser)obj;
		user.setOrgId(orgId);
        umUserDao.saveOrUpdate(user);
        
        //保存用户扩展信息
        UmUserExpand uue = uueService.getUmUserExpandById(user.getUserId());
        uue.setDevOrg(user.getDevOrg());
        uue.setDevOrgAddress(user.getDevOrgAddress());
        uue.setEmail(user.getEmail());
        uue.setPhoneNum(user.getPhoneNum());
        uue.setRemark(user.getRemark());
        uue.setSex(user.getSex());
        uue.setTelephone(user.getTelephone());
        
        this.uueService.save(uue);
        
        if(obj.getUserId() > 0) {
            object.put("success", true);
        } else {
            object.put("success", false);
            object.put("msg", "保存失败！");
        }
    }

    public void delete(JSONObject object, int id) {
        UmUser obj = umUserDao.queryById(id);
        if(null != obj) {
            obj.setUserStatus(UserStatusEnums.USER_STATUS_CANCLE.getKey());
            umUserDao.delete(obj);
            object.put("success", true);
        } else {
            object.put("success", false);
            object.put("msg", "删除失败！");
        }
    }
    
    public UmUser getObj(int id) {
    	UmUser obj = umUserDao.queryById(id);
        return obj;
    }
    
	/**
     * 检查登录帐号是否唯一
     * 
     * @param uaccount
     * @param object
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	@Override
    public void checkAccount(String uaccount, JSONObject object) {
		UmUser umUser = (UmUser) this.getByName(uaccount);
        if(null != umUser) {
            object.put("success", true);
            object.put("msg", "帐号已经存在，请输入其他帐号！");
        } else {
            object.put("success", false);
            object.put("msg", "帐号可以添加！");
        }
    }
	
    @Override
    public UmUser getById(int id) {
        String hql = "from UmUser u where u.userId = ? and userStatus = 1";
        UmUser umUser = umUserDao.queryForObject(hql, new Object[]{id});
        if(null != umUser) {
        	umUser.getUmRoles().iterator();
        }
        return umUser;
    }
    
    @Override
    public UmUser getByIdNoStatus(int id){
    	String hql = "from UmUser u where u.userId = ?";
        UmUser umUser = umUserDao.queryForObject(hql, new Object[]{id});
        if(null != umUser) {
        	umUser.getUmRoles().iterator();
        }
        return umUser;
    }

    @Override
    public BaseUser getByName(String username) {
        String hql = "from UmUser u where u.userAccount = ? and userStatus = 1";
        UmUser umUser = umUserDao.queryForObject(hql, new String[] { username });
        if(null != umUser) {
            Hibernate.initialize(umUser.getUmRoles());
        }
        return umUser;
    }
    
    @Override
    public String findRoleByUser(int userId) {
        StringBuffer sb = new StringBuffer();
        String sql = "select r.* from UM_ROLE r inner join UM_USER_ROLE ur on ur.role_id = r.role_id " +
        		"inner join UM_USER u on ur.user_id = u.user_id where u.user_id=:userId";
        SQLQuery query = umUserDao.getSession().createSQLQuery(sql).addEntity(Role.class);
        query.setParameter("userId", userId);
        List<Role> lists = query.list();
        if(null != lists && lists.size() > 0) {
            for(Role r : lists){
                sb.append(r.getRoleId()).append(",");
            }
        }
        return sb.toString();
    }

    @Override
    public List<UmUser> getUserByOrg(Integer orgId) {
//        String sql = "select * from UM_USER where org_id=:orgId and user_status=1 order by user_regist_time asc";
        String sql = "select * from UM_USER where org_id=:orgId order by user_regist_time asc";
        SQLQuery query = this.umOrgDao.getSession().createSQLQuery(sql).addEntity(UmUser.class);
        query.setParameter("orgId", orgId);
        return query.list();
    }
    
    @Override
    public List<Role> getRoleByUserId(int userId) {
        String sql = "select r.* from UM_ROLE r inner join UM_USER_ROLE ur on ur.role_id = r.role_id " +
                "inner join UM_USER u on ur.user_id = u.user_id where u.user_id=:userId";
        SQLQuery query = umUserDao.getSession().createSQLQuery(sql).addEntity(Role.class);
        query.setParameter("userId", userId);
        List<Role> lists = query.list();
        if(null != lists && lists.size() > 0) {
            return lists;
        }
        return null;
    }
    
    public void getUserAndExpandById(JSONObject object, Integer id){
    	UmUser user = new UmUser();
    	String sql = "select u.user_id,u.USER_STATUS,u.USER_ACCOUNT,u.USER_PASSWORD,u.USER_NAME,ue.phone_num,ue.telephone,ue.sex,ue.email,ue.dev_org,ue.dev_org_address,ue.remark from um_user u inner join um_user_expand ue on u.user_id = ue.user_id where u.user_id=:userId";
    	SQLQuery query = umUserDao.getSession().createSQLQuery(sql);
        query.setParameter("userId", id);
        List<Object[]> lists = query.list();
        if(null != lists && lists.size() > 0) {
        	object.put("success", true);
        	Object[] objArr = lists.get(0);
    		user.setUserId(Integer.parseInt(objArr[0].toString()));
    		user.setUserStatus(Integer.parseInt(objArr[1].toString()));
    		user.setUserAccount(objArr[2].toString());
    		user.setUserPassword(objArr[3].toString());
    		user.setUserName(objArr[4].toString());
    		user.setPhoneNum(objArr[5].toString());
    		user.setTelephone(objArr[6] != null ?objArr[6].toString():"");
    		user.setSex(Integer.parseInt(objArr[7].toString()));
    		user.setEmail(objArr[8].toString());
    		user.setDevOrg(objArr[9].toString());
    		user.setDevOrgAddress(objArr[10] != null ?objArr[10].toString():"");
    		user.setRemark(objArr[11] != null ?objArr[11].toString():"");
        }else{
        	object.put("success", false);
        	object.put("msg","该用户不存在或已删除！");
        }
        object.put("objPo", user);
    }

    @Override
    public List<UmUser> findUserListByOrgArr(String[] orgArr){
    	List<UmUser> rtnList = new ArrayList<UmUser>();
    	StringBuilder sql = new StringBuilder("select user_id,org_id from um_user where org_id in (");
    	sql.append(orgArr[0]);
    	for(int i = 1;i < orgArr.length;i++){
    		sql.append(",").append(orgArr[i]);
    	}
    	sql.append(")");
    	SQLQuery query = this.umUserDao.getSession().createSQLQuery(sql.toString());
    	List<Object[]> objList = query.list();
    	if(objList.size() > 0){
    		for(Object[] obj : objList){
    			UmUser po = new UmUser();
    			po.setUserId(Integer.parseInt(obj[0].toString()));
    			po.setOrgId(Integer.parseInt(obj[1].toString()));
    			rtnList.add(po);
    		}
    	}
    	return rtnList;
    }
}