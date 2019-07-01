package com.uca.ucasecurity.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.capinfo.auth.dao.UserDao;
import cn.com.capinfo.auth.pojos.User;
import cn.com.capinfo.core.pojos.BaseRole;
import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.service.RoleService;
import cn.com.capinfo.core.utils.SendEmail;
import cn.com.capinfo.core.utils.SystemProperties;

import com.uca.constants.UcaConstants;
import com.uca.ucasecurity.dao.UmOrgDao;
import com.uca.ucasecurity.dao.UmUserDao;
import com.uca.ucasecurity.dao.UmUserExpandDao;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.utils.DESUtil;

@Service
public class UcaUserServiceImpl {
	@Autowired
	private UserDao userDao;
	
	@Autowired
    private UmOrgDao umOrgDao;
	
	@Autowired
    private UmUserDao umUserDao;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UmUserExpandDao umUserExpandDao;
	
	public List<BaseUser> getHandingPersonListByOrgId(Integer orgId){
		String sql = "select * from um_user as u inner join um_user_expand uue on u.user_id = uue.user_id and uue.org_id =:orgId inner join um_user_role as ur on u.user_id = ur.user_id inner join um_role as r on ur.role_id = r.role_id and r.role_code =:roleCode";
		SQLQuery query = this.userDao.getSession().createSQLQuery(sql).addEntity(User.class);
		query.setParameter("orgId", orgId);
		query.setParameter("roleCode", UcaConstants.UM_ROLE_CODE_HANDING);
		List<BaseUser> rtnLi = query.list();
		return rtnLi;
	}
	
	public List<BaseUser> getHandingPersonList(){
		String sql = "select * from um_user as u inner join um_user_expand uue on u.user_id = uue.user_id inner join um_user_role as ur on u.user_id = ur.user_id inner join um_role as r on ur.role_id = r.role_id and r.role_code =:roleCode";
		SQLQuery query = this.userDao.getSession().createSQLQuery(sql).addEntity(User.class);
		query.setParameter("roleCode", UcaConstants.UM_ROLE_CODE_HANDING);
		List<BaseUser> rtnLi = query.list();
		return rtnLi;
	}
	
	public void saveUser(UmUser user, UmUserExpand umUserExpand, String userPassword) throws Throwable{ 
		//设置固定的角色信息（建设单位用户）
		Set<BaseRole> umRoles = new HashSet<BaseRole>();
		List<String> queryList = new ArrayList<String>();
		queryList.add(UcaConstants.UM_ROLE_CODE_USER);
		List<? extends BaseRole> roleList = this.roleService.findByCode(queryList);
		umRoles.add(roleList.get(0));
		user.setUmRoles(umRoles);
		user.setUserStatus(0);//新注册用户需要激活才能使用
		//注册用户默认挂载到"建设单位"机构下
		int orgId = Integer.parseInt(SystemProperties.getProperty("org.default.id"));
		user.setOrgId(orgId);
        
		//保存用户到数据库中,持久化
		this.umUserDao.saveOrUpdateForFlush(user);
		
		umUserExpand.setOrgId(orgId);
		//判断用户类型
        UmOrg org = umOrgDao.queryById(orgId);
        umUserExpand.setType(org.getType());//与机构类型保持一致
		
		umUserExpand.setUserId(user.getUserId());
		umUserExpand.setApplyActivateTime(new Date());
		this.umUserExpandDao.saveOrUpdateForFlush(umUserExpand);
		
		//对用户id和密码进行加密
		String encryptStr = DESUtil.encrypt(user.getUserId() + "," + userPassword + "," + new Date().getTime(), SystemProperties.getProperty("des.key"));
		System.out.println(SystemProperties.getProperty("activate.url") + encryptStr);
		//发送激活邮件到用户邮箱中
		if("1".equals(SystemProperties.getProperty("use.mail"))){
			
			boolean flag = SendEmail.sendSSLMessage(umUserExpand.getEmail(), "您的城市建设档案馆系统账号已申请成功，请激活！", SystemProperties.getProperty("activate.url") + encryptStr, "城市建设档案馆系统");
			if(!flag)
				throw new RuntimeException("邮件发送失败");
		}
	}
	
	
	
	/**
     * 检查登录帐号是否唯一
     * 
     * @param uaccount
     * @param object
     */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public boolean checkAccount(String uaccount) {
		String hql = "from User u where u.userAccount = ?";
		User user = this.userDao.queryForObject(hql, new String[] { uaccount });
        if(null != user) {
            return true;
        } else {
            return false;
        }
    }
	
	/**
	 * 检查邮箱是否唯一
	 * @param email
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public boolean checkEmail(String email) {
		String hql = "from UmUserExpand u where u.email = ?";
		UmUserExpand expand = this.umUserExpandDao.queryForObject(hql, new String[] { email });
		if(null != expand) {
            return true;
        } else {
            return false;
        }
	}

	public User getUser(Integer userId){
		String hql = "from User u where u.userId = ?";
		User user = this.userDao.queryForObject(hql, new Integer[] { userId });
		return user;
	}
	
	public UmUserExpand getUserExpand(Integer userId){
		String hql = "from UmUserExpand u where u.userId = ?";
		UmUserExpand expand = this.umUserExpandDao.queryForObject(hql, new Integer[] { userId });
		return expand;
	}
	
	/**
	 * 重新发送激活邮件
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public int updateRetryActivate(User user, String password) throws Exception{
		int flag = 1;
		if(user == null){
			flag = 2;//查无此用户
		}else{
			if(user.getUserStatus() == 1){
				flag = 3;//无需发送激活邮件
			}else{
				UmUserExpand expand = this.getUserExpand(user.getUserId());
				//发送激活邮件到用户邮箱中
				if("1".equals(SystemProperties.getProperty("use.mail"))){
					String encryptStr = DESUtil.encrypt(user.getUserId() + "," + password + "," + new Date().getTime(), SystemProperties.getProperty("des.key"));
					boolean fg = SendEmail.sendSSLMessage(expand.getEmail(), "您的城市建设档案馆系统账号已重新发送激活邮件，请激活！", SystemProperties.getProperty("activate.url") + encryptStr, "城市建设档案馆系统");
					if(!fg)
						throw new RuntimeException("邮件发送失败");
				}
				expand.setApplyActivateTime(new Date());
				this.umUserExpandDao.update(expand);
			}
		}
		return flag;
	}
	
	/**
	 * 检查并激活用户
	 * @param userId
	 * @param password
	 * @return
	 */
	public int updateApplyActivate(User user){
		int flag = 1;
		if(user == null){
			flag = 2;//查无此用户
		}else{
			if(user.getUserStatus() == 1){
				flag = 3;//重复激活
			}else{
				UmUserExpand expand = this.getUserExpand(user.getUserId());
				//判断是否超过激活时间限制
				if((new Date().getTime() - expand.getApplyActivateTime().getTime()) > Integer.valueOf(SystemProperties.getProperty("activate.time"))){
					flag = 4;
				}else{
					user.setUserStatus(1);
					this.userDao.saveOrUpdate(user);
				}
			}
		}
		return flag;
	}
}
