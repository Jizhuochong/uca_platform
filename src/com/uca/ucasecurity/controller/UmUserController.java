package com.uca.ucasecurity.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.auth.pojos.Role;
import cn.com.capinfo.auth.service.impl.RoleServiceImpl;
import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.MD5;
import cn.com.capinfo.core.utils.Page;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.service.UmUserService;

@Controller
@RequestMapping("/umuser")
public class UmUserController extends BaseController<UmUser> {
    private Logger logger = LoggerFactory.getLogger(UmUserController.class);
    private static MD5 md5 = new MD5();

    @Autowired
    private UmUserService umUserService;

    @Autowired
    private RoleServiceImpl roleService;

    private List<Role> initRoleList() {
        List<Role> roleList = new ArrayList<Role>();
        roleList = (List<Role>) roleService.findValidRoles();
        return roleList;
    }

    @RequestMapping("/indexGrid")
    public String index(Model model) throws Exception {
        model.addAttribute("roleList", initRoleList());
        return "uca/umuser/umuser_list";
    }

    @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject list(@RequestParam(required = false) String uaccount,
            @RequestParam(required = false) String uname, @RequestParam(required = false) String orgId,
            @RequestParam(required = false) String sd, @RequestParam(required = false) String ed) {
        logger.info("To List...");
        Page<UmUser> page = this.getPageRows();
        JSONObject object = new JSONObject();
        umUserService.findDatas(object, page, uaccount, uname, orgId, sd, ed);
        return object;
    }

    @RequestMapping(value = "/saveGrid", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject saveGrid(@ModelAttribute UmUser obj) throws Exception {
        JSONObject object = new JSONObject();
        if (obj.getUserId() > 0) {// 修改用户
            UmUser us = (UmUser) umUserService.getByIdNoStatus(obj.getUserId());
            if (null == us) {
                object.put("success", false);
                object.put("msg", "用户不存在或已经删除！");
                return object;
            }
            if (null != obj.getUserPassword() && !"".equals(obj.getUserPassword())
                    && !us.getUserPassword().equals(obj.getUserPassword())) {
                String pwd = obj.getUserPassword();
                obj.setUserPassword(md5.getMD5ofStr(pwd));
                obj.setUserRegistTime(us.getUserRegistTime());
            } else {
                obj.setUserPassword(us.getUserPassword());
                obj.setUserRegistTime(us.getUserRegistTime());
            }
            // obj.setUserStatus(us.getUserStatus());
        } else {// 新增用户
            if (null != obj.getUserPassword() && !"".equals(obj.getUserPassword())) {
                String pwd = obj.getUserPassword();
                obj.setUserPassword(md5.getMD5ofStr(pwd));
                obj.setUserRegistTime(new Date());
                // obj.setUserStatus(UserStatusEnums.USER_STATUS_USE.getKey());
            }
        }

        String[] roleIds = null;
        String roles = obj.getRoles();
        if (ParameterUtil.isNotBlank(roles)) {
            if (roles.endsWith(",")) {
                roles = roles.substring(0, roles.length() - 1);
            }
            roleIds = roles.split(",");
        }
        umUserService.save(object, obj, roleIds);
        return object;
    }

    @RequestMapping(value = "/saveGridForJsdw", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject saveGridForJsdw(@ModelAttribute UmUser obj) throws Exception {
        JSONObject object = new JSONObject();

        UmUser us = (UmUser) umUserService.getByIdNoStatus(obj.getUserId());
        if (null == us) {
            object.put("success", false);
            object.put("msg", "用户不存在或已经删除！");
            return object;
        }
        if (null != obj.getUserPassword() && !"".equals(obj.getUserPassword())
                && !us.getUserPassword().equals(obj.getUserPassword())) {
            String pwd = obj.getUserPassword();
            obj.setUserPassword(md5.getMD5ofStr(pwd));
            obj.setUserRegistTime(us.getUserRegistTime());
        } else {
            obj.setUserPassword(us.getUserPassword());
            obj.setUserRegistTime(us.getUserRegistTime());
        }
        obj.setUserStatus(1);
        umUserService.save(object, obj);

        return object;
    }

    @RequestMapping(value = "/getObjById", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = false) Integer id) throws Exception {
        JSONObject object = new JSONObject();
        this.umUserService.getUserAndExpandById(object, id);
        return object;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONObject delete(int id) throws Exception {
        JSONObject object = new JSONObject();
        umUserService.delete(object, id);
        return object;
    }

    @RequestMapping(value = "/fkComboData", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONArray getFkComboData() {
        JSONArray jsonArray = new JSONArray();
        List<UmUser> users = umUserService.findDatas();
        if (null != users && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                UmUser umUser = users.get(i);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", umUser.getUserId());
                jsonObject.put("name", umUser.getUserName());
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    /**
     * 检查登录帐号是否唯一
     * 
     * @param uaccount
     */
    @RequestMapping(value = "/checkAccount", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject checkAccount(String uaccount) {
        JSONObject object = new JSONObject();
        umUserService.checkAccount(uaccount, object);
        return object;
    }

}
