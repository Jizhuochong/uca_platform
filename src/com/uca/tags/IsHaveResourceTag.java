package com.uca.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import cn.com.capinfo.core.utils.SpringContextHolder;
import cn.com.capinfo.auth.service.impl.ResourceServiceImpl;

public class IsHaveResourceTag extends SimpleTagSupport  {
    
    private ResourceServiceImpl resourceService = SpringContextHolder.getBean(ResourceServiceImpl.class);

    // 自定义标签属性，用于标签传入参数
    private String url;
    private String userid;

    @Override
    public void doTag() throws JspException, IOException {
//        if(resourceService.isHaveResource(Integer.parseInt(userid), url)) {
//            this.getJspBody().invoke(null); // 有权限输出标签体内容
//        }
        if(resourceService.isHaveRes(Integer.parseInt(userid), url)) {
            this.getJspBody().invoke(null); // 有权限输出标签体内容
        }
    }

    public String getUserid() {
        return userid;
    }

    // 接收标签传入的参数
    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUrl() {
        return url;
    }

    // 接收标签传入的参数
    public void setUrl(String url) {
        this.url = url;
    }

}
