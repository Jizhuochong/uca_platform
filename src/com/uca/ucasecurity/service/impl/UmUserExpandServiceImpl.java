package com.uca.ucasecurity.service.impl;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uca.ucasecurity.dao.UmUserExpandDao;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.ucasecurity.service.UmUserExpandService;

/**
 * 用户扩展属性表service实现
 * 
 * @author mamm
 * 2014年12月2日
 * 
 */
@Service
public class UmUserExpandServiceImpl implements UmUserExpandService {
	@Autowired
	private UmUserExpandDao umUserExpandDao;
	
	/**
	 * 用户扩展信息保存 
	 * @author mamm
	 * 2014年12月2日
	 * @param po 机构对象 
	 * 
	 */
	@Override
	public void save(UmUserExpand po) {
		// TODO Auto-generated method stub
		this.umUserExpandDao.saveOrUpdate(po);
	}

	/**
	 * 根据id查询用户扩展信息
	 * @author mamm
	 * 2014年12月2日
	 * @param userId 用户id
	 * @return 用户扩展信息对象
	 */
	@Override
	public UmUserExpand getUmUserExpandById(Integer userId) {
		// TODO Auto-generated method stub
		return this.umUserExpandDao.queryById(userId);
	}
	/**根据用户身份标识查找用户id
	 * @author asp_red
	 * 2015年1月5日10:33:01
	 * 
	 */
	@Override
	public UmUserExpand find(int i) {
		String sql = "from MessageUser where userIdCode = ?";
		UmUserExpand query = umUserExpandDao.queryForObject(sql, new Object[]{i});
		return query;
		}

}
