package com.uca.ucasecurity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.auth.pojos.Role;
import cn.com.capinfo.auth.service.impl.RoleServiceImpl;
import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.ParameterUtil;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.service.UmOrgService;
import com.uca.ucasecurity.service.UmUserService;

@Controller
@RequestMapping("/orgusertree/*")
public class OrgUserTreeController extends BaseController {
    
    @Autowired
    private UmOrgService umOrgService;
    @Autowired
    private UmUserService umUserService;
    
    @Autowired
    private RoleServiceImpl roleService;
    
    private List<Role> initRoleList() {
        List<Role> roleList = new ArrayList<Role>();
        roleList = (List<Role>) roleService.findValidRoles();
        return roleList;
    }
    
    @RequestMapping(value = "/toTree", method = { RequestMethod.GET,RequestMethod.POST })
    public String toTree(Model model) throws Exception {
        model.addAttribute("roleList", initRoleList());
        return "uca/orguser/tree";
    }
    
    @RequestMapping(value = "/toTreeBy22", method = { RequestMethod.GET,RequestMethod.POST })
    public String toTreeBy22(Model model) throws Exception {
        model.addAttribute("roleList", initRoleList());
        return "uca/orguser/jtree";
    }
    
    @RequestMapping(value = "/tree", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONArray tree(@RequestParam(required = true) int pid) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<UmOrg> orgList = umOrgService.getUmOrgTree(pid);
        if(null != orgList && orgList.size() > 0) {
            for(UmOrg po : orgList) {
                JSONObject object = new JSONObject();
                object.put("id", po.getOrgId());
                object.put("name", po.getOrgName());
                object.put("ty", "1");
                object.put("org", "");
                object.put("userId", "");
                object.put("userAccount", "");
                object.put("userName", "");
                object.put("userPassword", "");
                object.put("orgId", "");
                object.put("roleName", "");
                object.put("roles", "");
                List<UmUser> children = umUserService.getUserByOrg(po.getOrgId());
                if(children != null && children.size() > 0) {
                    JSONArray childrenArray = new JSONArray();
                    for(UmUser user : children) {
                        JSONObject uobj = new JSONObject();
                        uobj.put("id", "user_"+user.getUserId());
                        uobj.put("name", user.getUserName());
                        uobj.put("ty", "2");
                        uobj.put("org", po.getOrgName());
                        
                        uobj.put("userId", user.getUserId());
                        uobj.put("userAccount", user.getUserAccount());
                        uobj.put("userName", user.getUserName());
                        uobj.put("userPassword", user.getUserPassword());
                        uobj.put("orgId", user.getOrgId());
                        
                        List<Role> rolesList = umUserService.getRoleByUserId(user.getUserId());
                        StringBuffer sb = new StringBuffer();
                        for(Role r : rolesList){
                            sb.append(r.getRoleName()).append(",");
                        }
                        String roleName = sb.toString();
                        if(roleName.endsWith(",")) {
                            roleName = roleName.substring(0, roleName.length() - 1);
                        }
                        uobj.put("roleName", roleName);
                        
                        String roles = umUserService.findRoleByUser(user.getUserId());
                        if(ParameterUtil.isNotEmpty(roles) && roles.length() > 0) {
                            uobj.put("roles", roles);
                        } else {
                            uobj.put("roles", "");
                        }
                        
                        childrenArray.add(uobj);
                    }
                    object.put("children", childrenArray);
                }
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }
    
    @RequestMapping(value = "/treeBy22", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONArray tree(@RequestParam(required = true) int pid,@RequestParam(required = true) int oid) throws Exception {
        JSONArray jsonArray = new JSONArray();
        List<UmOrg> orgList = umOrgService.getOrgTree(pid,oid);
        if(null != orgList && orgList.size() > 0) {
            for(UmOrg po : orgList) {
                JSONObject object = new JSONObject();
                object.put("id", po.getOrgId());
                object.put("name", po.getOrgName());
                object.put("ty", "1");
                object.put("org", "");
                object.put("userId", "");
                object.put("userAccount", "");
                object.put("userName", "");
                object.put("userPassword", "");
                object.put("orgId", "");
                object.put("roleName", "");
                object.put("roles", "");
                List<UmUser> children = umUserService.getUserByOrg(po.getOrgId());
                if(children != null && children.size() > 0) {
                    JSONArray childrenArray = new JSONArray();
                    for(UmUser user : children) {
                    	if(user.getUserStatus()==0){
                    		
                    		JSONObject uobj = new JSONObject();
                    		uobj.put("id", "user_"+user.getUserId());
                    		uobj.put("name", user.getUserName());
                    		uobj.put("ty", "2");
                    		uobj.put("org", po.getOrgName());
                    		
                    		uobj.put("userId", user.getUserId());
                    		uobj.put("userAccount", user.getUserAccount());
                    		uobj.put("userName", user.getUserName());
                    		uobj.put("userPassword", user.getUserPassword());
                    		uobj.put("orgId", user.getOrgId());
                    		
                    		List<Role> rolesList = umUserService.getRoleByUserId(user.getUserId());
                    		StringBuffer sb = new StringBuffer();
                    		for(Role r : rolesList){
                    			sb.append(r.getRoleName()).append(",");
                    		}
                    		String roleName = sb.toString();
                    		if(roleName.endsWith(",")) {
                    			roleName = roleName.substring(0, roleName.length() - 1);
                    		}
                    		uobj.put("roleName", roleName);
                    		
                    		String roles = umUserService.findRoleByUser(user.getUserId());
                    		if(ParameterUtil.isNotEmpty(roles) && roles.length() > 0) {
                    			uobj.put("roles", roles);
                    		} else {
                    			uobj.put("roles", "");
                    		}
                    		
                    		childrenArray.add(uobj);
                    	}
                    }
                    object.put("children", childrenArray);
                }
                jsonArray.add(object);
            }
        }
        return jsonArray;
    }

}
