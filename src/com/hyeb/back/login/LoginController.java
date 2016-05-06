/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.login;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.hyeb.Message;
import com.hyeb.Pageable;
import com.hyeb.Setting;
import com.hyeb.Setting.AccountLockType;
import com.hyeb.Setting.CaptchaType;
import com.hyeb.util.RSAUtils;
import com.hyeb.util.SettingUtils;
import com.hyeb.back.controller.BaseController;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;;

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
	public String login(ModelMap model, RedirectAttributes redirectAttributes,HttpServletRequest request) {			
		/** "私钥"参数名称 */
		final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";
		
		//HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 		
		Setting setting = SettingUtils.get();
		KeyPair keyPair = RSAUtils.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();		
		request.getSession().setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);

		String modulus = Base64.encodeBase64String(publicKey.getModulus().toByteArray());//N
		String exponent = Base64.encodeBase64String(publicKey.getPublicExponent().toByteArray());//e
		String captchaId = UUID.randomUUID().toString();
		boolean isBackCaptcha=ArrayUtils.contains(setting.getCaptchaTypes(), CaptchaType.adminLogin);		
		model.addAttribute("modulus", modulus);
		model.addAttribute("exponent", exponent);
		model.addAttribute("captchaId", captchaId);
		model.addAttribute("isBackCaptcha", isBackCaptcha);
		String messageStr = null;		
		String loginFailure = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		if (loginFailure != null) {
			if (loginFailure.equals("org.apache.shiro.authc.pam.UnsupportedTokenException")) {//无效令牌，验证码错误
				messageStr = "admin.captcha.invalid";
			} else if (loginFailure.equals("org.apache.shiro.authc.UnknownAccountException")) {//无此用户
				messageStr = "admin.login.unknownAccount";
			} else if (loginFailure.equals("org.apache.shiro.authc.DisabledAccountException")) {//禁用用户
				messageStr = "admin.login.disabledAccount";//
			} else if (loginFailure.equals("org.apache.shiro.authc.LockedAccountException")) {//锁定用户
				messageStr = "admin.login.lockedAccount";
			} else if (loginFailure.equals("org.apache.shiro.authc.IncorrectCredentialsException")) {//密码不正月
				
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					messageStr = "admin.login.accountLockCount";//密码错误，若连续{0}次密码错误账号将被锁定
				} else {
					messageStr = "admin.login.incorrectCredentials";//用户名或密码错误
				}
			} else if (loginFailure.equals("org.apache.shiro.authc.AuthenticationException")) {//其他未指定异常
				messageStr = "admin.login.authentication";//账号认证失败
			}
			if(messageStr!=null){
				Message message=Message.warn(messageStr);
				addFlashMessage(redirectAttributes, message);
			}
		}		
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