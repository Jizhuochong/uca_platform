package com.uca.ucasecurity.service;

import com.uca.ucasecurity.pojos.UmUserExpand;


/**
 * 用户扩展信息service
 * @author mamm
 * 2014年12月2日
 *
 */
public interface UmUserExpandService {
	/**
	 * 用户扩展信息保存 
	 * @author mamm
	 * 2014年12月2日
	 * @param po 机构对象 
	 * 
	 */
	void save(UmUserExpand po);
	
	/**
	 * 根据id查询用户扩展信息
	 * @author mamm
	 * 2014年12月2日
	 * @param userId 用户id
	 * @return 用户扩展信息对象
	 */
	UmUserExpand getUmUserExpandById(Integer userId);
	/**根据用户身份标识查找用户id
	 * @author asp_red
	 * 2015年1月5日10:33:52
	 * @param i
	 * @return
	 */
	UmUserExpand find(int i);
}
