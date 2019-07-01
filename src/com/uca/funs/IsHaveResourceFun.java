package com.uca.funs;

import cn.com.capinfo.core.utils.SpringContextHolder;
import cn.com.capinfo.auth.service.impl.ResourceServiceImpl;

public class IsHaveResourceFun {
    
    public static boolean isHaveResource(String url, Integer userid) {
        ResourceServiceImpl resourceService = SpringContextHolder.getBean(ResourceServiceImpl.class);
//        if(resourceService.isHaveResource(userid, url)) {
//            return true;
//        }
        if(resourceService.isHaveRes(userid, url)) {
            return true;
        }
        return false;
    }

}
