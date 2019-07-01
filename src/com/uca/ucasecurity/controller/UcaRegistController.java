package com.uca.ucasecurity.controller;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.com.capinfo.auth.pojos.User;
import cn.com.capinfo.core.controller.BaseController;
import cn.com.capinfo.core.utils.MD5;
import cn.com.capinfo.core.utils.SpringContextHolder;
import cn.com.capinfo.core.utils.SystemProperties;

import com.alibaba.fastjson.JSONObject;
import com.octo.captcha.service.CaptchaService;
import com.uca.ucasecurity.pojos.UmUser;
import com.uca.ucasecurity.pojos.UmUserExpand;
import com.uca.ucasecurity.service.impl.UcaUserServiceImpl;
import com.uca.utils.DESUtil;

@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("/ucaRegist")
public class UcaRegistController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(UcaRegistController.class);
    @Autowired
    private UcaUserServiceImpl ucaUserServiceImpl;
    
    private static MD5 md5 = new MD5();

    /**
     * 跳转注册填写信息页
     * 
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value="/toRegist", method={ RequestMethod.GET, RequestMethod.POST })
    public String toRegist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("To regist...");
        return "/regist";
    }
    
    /**
     * 跳转注册确认页
     * 
     * @return String
     * @throws Exception
     * */
    /*@RequestMapping(value = "/toConfirmRegist", method = { RequestMethod.GET, RequestMethod.POST })
    public String toConfirmRegist() throws Exception {
        logger.info("To Regist Confirm...");
        // 记录上一个访问url地址
        String registBackurl = this.getRequest().getHeader("Referer");
        this.getSession().setAttribute("registBackurl", registBackurl);
        return "usercenter/imgsys/regist_confirm";
    }*/
    
    /**
     * 跳转注册成功页
     * 
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value = "/regist", method = { RequestMethod.GET, RequestMethod.POST })
    public String regist(Model model) throws Exception {
        logger.info("To Regist...");
        String userAccount = this.transform(this.getRequest().getParameter("userAccount"));
        String userPassword = this.transform(this.getRequest().getParameter("userPassword"));
        String phoneNum = this.transform(this.getRequest().getParameter("phoneNum"));
        String telephone = this.transform(this.getRequest().getParameter("telephone"));
        String userName = this.transform(this.getRequest().getParameter("userName"));
        String sex = this.transform(this.getRequest().getParameter("sex"));
        String email = this.transform(this.getRequest().getParameter("email"));
        String devOrg = this.transform(this.getRequest().getParameter("devOrg"));
        String devOrgAddress = this.transform(this.getRequest().getParameter("devOrgAddress"));
        String remark = this.transform(this.getRequest().getParameter("remark"));
        
        //用户基本
        UmUser user = new UmUser();
        user.setUserAccount(userAccount);
        user.setUserName(userName);
        user.setUserPassword(md5.getMD5ofStr(userPassword));
		user.setUserRegistTime(new Date());
		
		//用户扩展信息
		UmUserExpand umUserExpand = new UmUserExpand();
		umUserExpand.setType(1);//普通用户
		umUserExpand.setPhoneNum(phoneNum);
		umUserExpand.setTelephone(telephone);
		umUserExpand.setSex(Integer.valueOf(sex));
		umUserExpand.setEmail(email);
		umUserExpand.setDevOrg(devOrg);
		umUserExpand.setDevOrgAddress(devOrgAddress);
		umUserExpand.setRemark(remark);
		
		try{
			this.ucaUserServiceImpl.saveUser(user, umUserExpand, userPassword);
			model.addAttribute("success", true);
			model.addAttribute("msg", umUserExpand.getEmail());
			model.addAttribute("registBackurl", "");
		}catch(Throwable e){
			model.addAttribute("success", false);
			model.addAttribute("msg", "注册过程中出现异常，请重试！");
	        model.addAttribute("registBackurl", "/ucaRegist/toRegist");
		}
        return "/regist_success";
    }
    
    /**
     * 验证用户名是否可用
     * 
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value = "/regValidAccount", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject regValidAccount() throws Exception {
        logger.info("To Regist Success...");
        JSONObject object = new JSONObject();
        String account = this.getRequest().getParameter("param");
        boolean flag = ucaUserServiceImpl.checkAccount(account);
        if(!flag) {
            object.put("status", "y");
            object.put("info", "用户账号验证通过，可以使用！");
        } else {
            object.put("status", "n");
            object.put("info", "");
        }
        return object;
    }
    
    /**
     * 找回密码时判断用户名是否已经注册
     * 
     * @return String
     * @throws Exception
     * */
    /*@RequestMapping(value = "/isValidAccount", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject isValidAccount() throws Exception {
        logger.info("To Regist Success...");
      JSONObject object = new JSONObject();
      String account = this.getRequest().getParameter("param");
      ImgUser iuser = imgUserService.getByAccount(account);
      if(null != iuser) {
          object.put("status", "y");
          object.put("info", "用户名存在，可以找回密码！");
      } else {
          object.put("status", "n");
          object.put("info", "用户不存在！");
      }
      return object;
    }*/
    
    /**
     * 验证邮箱是否可用
     * 
     * @return String
     * @throws Exception
     * */
    @RequestMapping(value = "/regValidEmail", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject regValidEmail() throws Exception {
        logger.info("To Regist Success...");
        JSONObject object = new JSONObject();
        String email = this.getRequest().getParameter("param");
        boolean flag = ucaUserServiceImpl.checkEmail(email);
        if(!flag) {
            object.put("status", "y");
            object.put("info", "邮箱验证通过，可以使用！");
        } else {
            object.put("status", "n");
            object.put("info", "");
        }
        return object;
    }
    
    CaptchaService captchaService = SpringContextHolder.getBean("captchaService");
    
    /**
     * 检查验证码
     * @param orderNum
     * @return
     */
    @RequestMapping(value="/checkIdentifyingCode", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject checkIdentifyingCode(){
    	JSONObject object = new JSONObject();
        String code = this.getRequest().getParameter("param");
    	String captchaID = this.getSession().getId();
    	boolean flag = false;
    	try{
    		flag = captchaService.validateResponseForID(captchaID, code);
    		if(flag) {
                object.put("status", "y");
                object.put("info", "已输入验证码！");
            } else {
                object.put("status", "n");
                object.put("info", "验证码输入错误！");
            }
    	}catch(Exception e){
    	}
    	return object;
    }
    
    @Autowired
    @Qualifier("org.springframework.security.authenticationManager")//编辑软件会提示错误
    protected AuthenticationManager authenticationManager;
    
    @RequestMapping(value = "/actA/{code}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView actA(@PathVariable("code") String code) throws Exception{
    	ModelAndView modelAndView = new ModelAndView();
		String decode = DESUtil.decrypt(code, SystemProperties.getProperty("des.key"));
		String[] decodeArr = decode.split(",");
		if(decodeArr.length == 3){
			String userId = decodeArr[0];
			String password = decodeArr[1];
			User user = this.ucaUserServiceImpl.getUser(Integer.valueOf(userId));
    		//验证激活是否有效
        	int flag = this.ucaUserServiceImpl.updateApplyActivate(user);
        	switch(flag){
        		case 1://有效进行免登录处理
        			modelAndView.addObject("success", true);
        			modelAndView.addObject("msg", "激活成功");
        			modelAndView.addObject("registBackurl", "/toIndex");
        			this.autoLogin(user.getUserAccount(), password, this.getRequest());
        			break;
        		case 2://查无此用户
        			modelAndView.addObject("success", false);
        			modelAndView.addObject("flag", 2);
        			modelAndView.addObject("msg", "没有此用户");
        			modelAndView.addObject("registBackurl", "/toRegist");
        			break;
        		case 3://重复激活
        			modelAndView.addObject("success", false);
        			modelAndView.addObject("flag", 3);
        			modelAndView.addObject("msg", "你已激活过该账户");
        			modelAndView.addObject("registBackurl", "/toLogin");
        			break;
        		case 4://超过激活时间限制
        			modelAndView.addObject("success", false);
        			modelAndView.addObject("flag", 4);
        			modelAndView.addObject("msg", "该链接已失效");
        			modelAndView.addObject("registBackurl", "/ucaRegist/retryAct/"+code);
        			break;
        	}
        	
		}else{
			//跳转到失败页面
			modelAndView.addObject("success", false);
			modelAndView.addObject("msg", "验证失败");
			modelAndView.addObject("registBackurl", "/toLogin");
		}
		modelAndView.setViewName("/activate_success");
    	return modelAndView;
    }
    
    private void autoLogin(String userAccount, String password, HttpServletRequest request){
    	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
    			userAccount, password);
    	try{
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication authenticatedUser = authenticationManager
                    .authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        }
        catch( AuthenticationException e ){
        	e.printStackTrace();
            System.out.println("Authentication failed: " + e.getMessage());
        }
    }
    
    @RequestMapping(value = "/retryAct/{code}", method = { RequestMethod.GET, RequestMethod.POST })
    public ModelAndView retryAct(@PathVariable("code") String code) throws Exception{
    	logger.info("To retryAct...");
    	ModelAndView modelAndView = new ModelAndView();
		String decode = DESUtil.decrypt(code, SystemProperties.getProperty("des.key"));
		String[] decodeArr = decode.split(",");
		if(decodeArr.length == 3){
			String userId = decodeArr[0];
			String password = decodeArr[1];
			User user = this.ucaUserServiceImpl.getUser(Integer.valueOf(userId));
			int flag = this.ucaUserServiceImpl.updateRetryActivate(user, password);
			switch(flag){
    		case 1://重新发送激活邮件成功
    			UmUserExpand expand = this.ucaUserServiceImpl.getUserExpand(user.getUserId());
    			modelAndView.addObject("success", true);
    			modelAndView.addObject("msg", "发送激活邮件成功");
    			modelAndView.addObject("email", expand.getEmail());
    			modelAndView.addObject("registBackurl", "/");
    			break;
    		case 2://查无此用户
    			modelAndView.addObject("success", false);
    			modelAndView.addObject("flag", 2);
    			modelAndView.addObject("msg", "没有此用户");
    			modelAndView.addObject("registBackurl", "/toRegist");
    			break;
    		case 3://重复激活
    			modelAndView.addObject("success", false);
    			modelAndView.addObject("flag", 3);
    			modelAndView.addObject("msg", "你已激活过该账户");
    			modelAndView.addObject("registBackurl", "/toLogin");
    			break;
    	}
		}else{
			//跳转到失败页面
			modelAndView.addObject("success", false);
			modelAndView.addObject("msg", "验证失败");
			modelAndView.addObject("registBackurl", "/toLogin");
		}
    	modelAndView.setViewName("/retry_activate_success");
    	return modelAndView;
    }
    
    /**
     * 验证手机是否可用
     * 
     * @return String
     * @throws Exception
     * */
    /*@RequestMapping(value = "/regValidMobile", method = { RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject regValidMobile() throws Exception {
        logger.info("To Regist Success...");
        JSONObject object = new JSONObject();
        String mobile = this.getRequest().getParameter("param");

        if(vr.isSuccess()) {
            object.put("status", "y");
            object.put("info", "手机号码验证通过，可以使用！");
        } else {
            object.put("status", "n");
            object.put("info", vr.getFailInfo());
        }
        return object;
    }*/
    public static void main(String[] args){
    	String regex = "^\\[\\d{4}\\]\\d{4}$";
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher("[2015]0001");
    	if(matcher.find())
    	System.out.println(matcher.group());
    }
}
