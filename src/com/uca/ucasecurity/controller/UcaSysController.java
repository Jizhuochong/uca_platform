package com.uca.ucasecurity.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.capinfo.auth.pojos.Resource;
import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.pojos.BaseResource;
import cn.com.capinfo.core.utils.SecurityIdentifyUtils;
import cn.com.capinfo.core.utils.SpringContextHolder;
import cn.com.capinfo.security.utils.SecurityUtils;

import com.octo.captcha.service.CaptchaService;
import com.uca.ucasecurity.service.impl.UcaResourceServiceImpl;

@Controller
public class UcaSysController extends BaseController{
    
    protected static Logger logger = LoggerFactory.getLogger(UcaSysController.class);
    
    @Autowired
    private UcaResourceServiceImpl ucaResourceServiceImpl;
    
    private SecurityIdentifyUtils secUtils = new SecurityUtils();

    /**
     * 跳转登录页
     * 
     * @return String
     * @throws Exception
     * */
    /*@RequestMapping(value="/toErmLogin", method={ RequestMethod.GET,RequestMethod.POST })
    public String toErmLogin() throws Exception {
        logger.info("To Login...");
        return "/login";
    }*/
    
    /**
     * 跳转自动登录页
     * @return
     * @throws Exception
     */
    /*@RequestMapping(value="/toErmAutoLogin", method={ RequestMethod.GET,RequestMethod.POST })
    public String toAutoErmLogin() throws Exception {
        return "/autoLogin";
    }*/
    
    /**
     * 登陆后页面
     * 
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value="/toIndex", method={ RequestMethod.GET,RequestMethod.POST })
    public String toIndex(Model model) throws Exception {
        logger.info("To Index...");
        
        List<String> roleCodes = new ArrayList<String>();
        Collection<GrantedAuthority> authorityCol = (Collection<GrantedAuthority>) secUtils.getAuthorities();
        if(authorityCol != null && authorityCol.size() > 0){
            for (GrantedAuthority ga : authorityCol) {
                roleCodes.add(ga.getAuthority());
            }
        }
        
        if(roleCodes.size() > 0) {
            StringBuffer sb = new StringBuffer();
            for(String code : roleCodes) {
                sb.append("'").append(code).append("'").append(",");
            }
            String rcodes = sb.toString().substring(0, sb.toString().length()-1);
            // 资源树
            List<? extends BaseResource> resList = ucaResourceServiceImpl.findIndexMenu(rcodes, 0);
            this.removePR(resList);
            
            // 前台页面左侧树型结构显示,如果注释掉,就使用动态项显示
            for(Resource resource : (List<Resource>) resList) {
                List<Resource> childrenList = (List<Resource>) resource.getChildrenList();
                StringBuffer msg = new StringBuffer();
                msg.append("<ul class='easyui-tree'>");
                this.retStringData(msg, childrenList);
                msg.append("</ul>");
                resource.setResourceUrl(msg.toString());
            }
            
            model.addAttribute("menuNodes", resList);
        }
        
        return "/index";
    }
    
    private void removePR(Collection<? extends BaseResource> resList) {
        if(null != resList && resList.size() > 0) {
            for(BaseResource res : resList){
                this.removePR(res.getChildrenList());
//                res.setUmPermissions(null);
                res.setUmRoles(null);
            }
        }
    }
    
    /**
     * 拒绝访问
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    /*@RequestMapping(value="/accessErmDenied", method={ RequestMethod.GET, RequestMethod.POST })
    public String accessErmDenied(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("To accessDenied...");
        return "/accessDenied";
    }*/
    
    /**
     * 关于系统
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/about", method={ RequestMethod.GET, RequestMethod.POST })
    public String about(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("To about...");
        return "/about";
    }
    
    private void retStringData(StringBuffer msg, List<Resource> resList) {
        if(null != resList && resList.size() > 0) {
            for(Resource res : resList) {
                msg.append("<li><span>");
                if(null != res.getResourceUrl() && res.getResourceUrl() != "") {
                    msg.append("<a id=\"a_").append(res.getResourceId()).append("\" href='javascript:void(0);' onclick='javascript:addTab(\"")
                        .append(res.getResourceId()).append("\",\"")
                        .append(res.getResourceName()).append("\",\"").append(res.getResourceUrl()).append("\"");
//                    if(null != res.getJsParameters()) {
//                        msg.append(",null)' ").append("jsParam=\"").append(res.getJsParameters().trim()).append("\"").append(">");
//                    } else {
                        msg.append(",null)'>");
//                    }
                    msg.append(res.getResourceName()).append("</a></span>");
                } else {
                    msg.append(res.getResourceName()).append("</span>");
                }
                if(null != res.getChildrenList() && res.getChildrenList().size() > 0) {
                    msg.append("<ul>");
                    this.retStringData(msg, (List<Resource>) res.getChildrenList());
                    msg.append("</ul>");
                }
                msg.append("</li>");
            }
        }
    }
    CaptchaService captchaService = SpringContextHolder.getBean("captchaService");

    /**
     * 检查验证码
     * @param orderNum
     * @return
     */
    @RequestMapping(value="/checkIdentifyingCode", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public String checkIdentifyingCode(@RequestParam(required = true) String code){
    	String captchaID = this.getSession().getId();
    	boolean flag = false;
    	try{
    		flag = captchaService.validateResponseForID(captchaID, code);
    	}catch(Exception e){
    	}
    	return "{\"flag\":"+flag+"}";
    }
}