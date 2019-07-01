package com.uca.ucasecurity.service;

import java.util.List;

import cn.com.capinfo.auth.pojos.Role;
import cn.com.capinfo.core.pojos.BaseUser;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSONObject;
import com.uca.ucasecurity.pojos.UmUser;

public interface UmUserService {

    void findDatas(JSONObject object, Page<? extends BaseUser> page, String uaccount, 
    		String uname, String orgId, String sd, String ed);

    List<UmUser> findDatas();
    
    void save(JSONObject object, BaseUser obj, String[] roleIds);
    
    void save(JSONObject object, BaseUser obj);

    void delete(JSONObject object, int id);

    UmUser getObj(int id);

    void checkAccount(String uaccount, JSONObject object);
    
    UmUser getById(int id);
    
    BaseUser getByName(String username);
    
    String findRoleByUser(int userId);
    
    UmUser getByIdNoStatus(int id);

    List<UmUser> getUserByOrg(Integer orgId);
    
    List<Role> getRoleByUserId(int userId);
    
    void getUserAndExpandById(JSONObject object, Integer id);
    
    List<UmUser> findUserListByOrgArr(String[] orgArr);
}
