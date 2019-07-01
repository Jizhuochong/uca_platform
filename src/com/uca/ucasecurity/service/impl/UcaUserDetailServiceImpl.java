package com.uca.ucasecurity.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.ucasecurity.service.UmOrgService;
import com.uca.ucasecurity.service.UmUserExpandService;

import cn.com.capinfo.core.pojos.BaseRole;
import cn.com.capinfo.core.service.UserService;
import cn.com.capinfo.core.utils.MD5;
import cn.com.capinfo.core.utils.PropertiesUtils;
import cn.com.capinfo.security.pojos.User;

/**
 * User detail query service queries the user by user name and permission
 * information
 * 
 * @author mamm
 * 
 */
@SuppressWarnings("deprecation")
@Service
public class UcaUserDetailServiceImpl implements UserDetailsService  {

	private Logger logger = LoggerFactory.getLogger(UcaUserDetailServiceImpl.class);

	@Autowired
	private ReloadableResourceBundleMessageSource messages;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UmUserExpandService umUserExpandService;
	
	@Autowired
	private UmOrgService umOrgService;
	
	@Autowired(required=false)
	private HttpServletRequest request;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		if (StringUtils.isEmpty(username)) {
			throw new UsernameNotFoundException(this.messages.getMessage(
					"username.not.empty", null, username, null));
		}

		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
//		userService = SpringContextHolder.getBean("userServiceImpl");
		cn.com.capinfo.core.pojos.BaseUser userPo = userService.getByName(username);
		User user = null;
		if (userPo == null) {
			logger.info("Did not find a user named '" + username + "'");
			throw new UsernameNotFoundException(this.messages.getMessage(
					"loginPage.authenticationFailure", null, username, null));
		} else {
			Set<? extends BaseRole> roleSet = userPo.getUmRoles();
			Iterator<? extends BaseRole> itt = roleSet.iterator();
			GrantedAuthority auth = null;
			while (itt.hasNext()) {
				auth = new GrantedAuthorityImpl(itt.next().getRoleCode());
				auths.add(auth);
			}
			user = new User(userPo.getUserId(), userPo.getUserAccount(),
					userPo.getUserName(), userPo.getUserPassword(),
					userPo.getUserRegistTime(), userPo.getUserStatus(), true,
					true, true, true, auths);
			//将用户扩展信息对象放入property中
			UmUserExpand exPo = this.umUserExpandService.getUmUserExpandById(userPo.getUserId());
			if(exPo != null){
				user.setProperty("userExpand", exPo);
				//获取机构信息并放入property中
				if(exPo.getOrgId() != null){
					UmOrg orgPo = this.umOrgService.getUmOrgById(exPo.getOrgId());
					user.setProperty("org", orgPo);
				}
			}
		}
		return user;
	}

}
