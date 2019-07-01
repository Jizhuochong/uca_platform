package com.uca.ucasecurity.service;

import java.util.List;

import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.ucasecurity.pojos.UmOrg;

/**
 * 机构service
 * 
 * @author mamm 2014年12月2日
 */
public interface UmOrgService {
    /**
     * 机构保存
     * 
     * @author mamm 2014年12月2日
     * @param po
     *            机构对象
     */
    void save(UmOrg po);

    /**
     * 根据id查询机构
     * 
     * @author mamm 2014年12月2日
     * @param orgId
     *            机构id
     * @return 机构对象
     */
    UmOrg getUmOrgById(Integer orgId);

    /**
     * @author asp_blue 2015年3月2日12:29:42 机构树
     */
    List<UmOrg> tree(Integer parentOrgId);

    void find(JSONObject object, Page<UmOrg> page, String orgCode, String sd, String ed);

    void saveOrUpdate(JSONObject object, UmOrg po);

    void getById(JSONObject object, int id);

    void delete(JSONObject object, int id);

    List<UmOrg> findDatas();

    List<UmOrg> tree(Integer parentOrgId, String type);

    List<UmOrg> tree(Integer parentOrgId, Integer type);

    List<UmOrg> treeForUser(UmOrg po, Integer parentOrgId, Integer type);

    List<UmOrg> getUmOrgTree(int pid);

	List<UmOrg> getOrgTree(int pid, int oid);
}
