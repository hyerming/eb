/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.login;

import javax.annotation.Resource;

import com.hyeb.Message;
import com.hyeb.Pageable;
import com.hyeb.entity.Role;
import com.hyeb.back.controller.BaseController;
import com.hyeb.back.role.RoleService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 登陆
 * 
 * @author hyerming
 * @version 3.0
 */
@Controller("backLoginController")
@RequestMapping("/back/login")
public class LoginController extends BaseController {

	/**
	 * 登陆页
	 */
	@RequestMapping(value = "/login")
	public String login() {
		Subject subject=SecurityUtils.getSubject();
		if(subject.isAuthenticated()){
			return "redirect:/back/main/main";
		}else{
			return "/back/login/login";
		}
		
	}
	/**
	 * 登陆
	 */
	@RequestMapping(value = "/loginaction")
	public String list() {		
		return "redirect:/back/main/main";
	}
}