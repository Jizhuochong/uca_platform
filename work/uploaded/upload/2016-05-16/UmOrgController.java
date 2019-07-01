package com.uca.ucasecurity.controller;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.Page;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.uca.ucasecurity.pojos.UmOrg;
import com.uca.ucasecurity.service.UmOrgService;

@Controller
@RequestMapping("/org/*")
public class UmOrgController extends BaseController<UmOrg> {
    private static SerializerFeature[] serializerFeatureNull = new SerializerFeature[] {
        SerializerFeature.DisableCircularReferenceDetect,
        SerializerFeature.WriteMapNullValue,
        SerializerFeature.WriteNullNumberAsZero,
        SerializerFeature.WriteNullStringAsEmpty};
    
    @Autowired
    private UmOrgService umOrgService;
    
    @RequestMapping(value = "/toList", method = { RequestMethod.GET,RequestMethod.POST })
    public String toList() throws Exception {
        return "uca/org/org_list";
    }
    
    @RequestMapping(value = "/list", method = { RequestMethod.POST })
    @ResponseBody
    public String list(@RequestParam(required = false) String orgCode, 
            @RequestParam(required = false) String sd, 
            @RequestParam(required = false) String ed) throws Exception {
        Page<UmOrg> page = this.getPageRows();
        JSONObject object = new JSONObject();
        
        umOrgService.find(object, page, orgCode, sd, ed);
        return JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss",serializerFeatureNull);
    }
    
    @RequestMapping(value = "/save", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject save(@RequestParam(required = true) String orgName, 
            @RequestParam(required = true) String orgCode, 
            @RequestParam(required = true) Integer sort, 
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer type) throws Exception{
        JSONObject object = new JSONObject();
        UmOrg po = new UmOrg();
        po.setParentOrgId(0);
        po.setOrgCode(orgCode);
        po.setOrgName(orgName);
        po.setDescription(description);
        po.setSort(sort);
        po.setType(type);
        umOrgService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/edit", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject edit(@RequestParam(required = true) String orgId, 
    		@RequestParam(required = true) String orgName, 
            @RequestParam(required = true) String orgCode, 
            @RequestParam(required = true) Integer sort, 
            @RequestParam(required = false) String description,
            @RequestParam(required = false) Integer type) throws Exception{
        JSONObject object = new JSONObject();
        UmOrg po = new UmOrg();
        po.setOrgId(Integer.valueOf(orgId));
        po.setOrgCode(orgCode);
        po.setOrgName(orgName);
        po.setDescription(description);
        po.setSort(sort);
        po.setType(type);
        umOrgService.saveOrUpdate(object, po);
        return object;
    }
    
    @RequestMapping(value = "/getObjById", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getObjById(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        umOrgService.getById(object, id);
        return object;
    }
    
    @RequestMapping(value = "/delete", method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject delete(@RequestParam(required = true) int id) throws Exception{
        JSONObject object = new JSONObject();
        umOrgService.delete(object, id);
        return object;
    }
    
    @RequestMapping(value = "/fkComboData", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONArray getFkComboData() {
	    JSONArray jsonArray = new JSONArray();
	    List<UmOrg> umOrgs = umOrgService.findDatas();
	    if(null != umOrgs && umOrgs.size() > 0) {
	        for(int i=0;i<umOrgs.size();i++) {
	        	UmOrg umOrg = umOrgs.get(i);
	            JSONObject jsonObject = new JSONObject();
	            jsonObject.put("id", umOrg.getOrgId());
	            jsonObject.put("name", umOrg.getOrgName());
	            jsonArray.add(jsonObject);
	        }
	    }
        return jsonArray;
    }

    /**
     * 机构用户树
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value="/orgAndUserTree", method={ RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public JSONArray getOrgAndUserTree(@RequestParam(required = false) Integer id, @RequestParam(required = false) Integer type, @RequestParam(required = false) String userIds) throws Exception {
    	if(id == null){
    		id = 0;
		}
        List<UmOrg> orgList = (List<UmOrg>)umOrgService.treeForUser(null, id, type);

        JSONArray array = new JSONArray();
        // 定义根节点
        JSONObject object = new JSONObject();
        
        object.put("id", 0);
        object.put("pId", -1);
        object.put("tp", 1);
        object.put("name", "机构用户树");
        object.put("open", true);
        array.add(object);
        
        HashMap<Integer, String> userMap = checkUserMap(userIds);
        
        retJsonData(array, orgList, userMap);
        return array;
    }
    
    private HashMap<Integer, String> checkUserMap(String userIds){
    	HashMap<Integer, String> userMap = new HashMap<Integer, String>(0);
    	if(StringUtils.isNotEmpty(userIds)){
    		String[] idArr = userIds.split(",");
    		for(String id : idArr){
    			userMap.put(Integer.valueOf(id), id);
    		}
    	}
    	return userMap;
    }
    
    private void retJsonData(JSONArray array, List<UmOrg> orgList, HashMap<Integer, String> userMap) {
        if(null != orgList && orgList.size() > 0) {
            for(UmOrg po : orgList) {
                JSONObject object = new JSONObject();
                object.put("id", po.getOrgId());
                object.put("pId", po.getParentOrgId());
                object.put("name", po.getOrgName());
                object.put("tp", po.getType());
                if(userMap.get(po.getOrgId()) != null){
                	object.put("checked", true);
                }
                array.add(object);
                if(null != po.getChildrenList() && po.getChildrenList().size() > 0) {
//                    object.put("open", true);
                    object.put("isParent", true);
                }
                this.retJsonData(array, po.getChildrenList(), userMap);
            }
        }
    }
}
