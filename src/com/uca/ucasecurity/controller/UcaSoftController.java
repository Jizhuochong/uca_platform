package com.uca.ucasecurity.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UcaSoftController{
    
    protected static Logger logger = LoggerFactory.getLogger(UcaSoftController.class);
    
    @RequestMapping(value="/toDownloadSoft", method={ RequestMethod.GET,RequestMethod.POST })
    public String toDownloadSoft() throws Exception {
        logger.info("To Download Soft...");
        return "/soft";
    }

}