/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.authenticate;
/**
 *UnknownAccountException //用户名未知...  
IncorrectCredentialsException //凭据不正确，例如密码不正确 ...  
LockedAccountException //用户被锁定，例如管理员把某个用户禁用...  
AuthenticationException   //其他未指定异常
DisabledAccountException（禁用的帐号）
ExcessiveAttemptsException（登录失败次数过多）
ExpiredCredentialsException（过期的凭证）
UnsupportedTokenException 无效令牌
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.hyeb.Principal;
import com.hyeb.Setting;
import com.hyeb.Setting.AccountLockType;
import com.hyeb.Setting.CaptchaType;
import com.hyeb.back.sysuser.SysUserService;
import com.hyeb.entity.SysUser;
import com.hyeb.util.SettingUtils;
import com.hyeb.util.SpringUtils;

/**
 * 权限认证
 * 
 * @author SHOP++ Team
 * @version 3.0
 */

public class AuthenticationRealm extends AuthorizingRealm {

	@Resource(name = "captchaServiceImpl")
	private CaptchaService captchaService;	
	/**
	 * 获取认证信息
	 * 
	 * @param token
	 *            令牌
	 * @return 认证信息
	 */
	@Override	
	protected AuthenticationInfo doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken token) {
		SysUserService sysUserService=(SysUserService)SpringUtils.getBean("sysUserServiceImpl");
		AuthenticationToken authenticationToken = (AuthenticationToken) token;
		String username = authenticationToken.getUsername();
		String password = new String(authenticationToken.getPassword());
		String captchaId = authenticationToken.getCaptchaId();
		String captcha = authenticationToken.getCaptcha();
		String ip = authenticationToken.getHost();
		if (!captchaService.isValid(CaptchaType.adminLogin, captchaId, captcha)) {
			throw new UnsupportedTokenException();
		}
		if (username != null && password != null) {
			SysUser sysUser = sysUserService.findByUsername(username);
			if (sysUser == null) {
				throw new UnknownAccountException();
			}
			if (!sysUser.getIsEnabled()) {
				throw new DisabledAccountException();
			}
			Setting setting = SettingUtils.get();
			if (sysUser.getIsLocked()) {
				if (ArrayUtils.contains(setting.getAccountLockTypes(), AccountLockType.admin)) {
					int loginFailureLockTime = setting.getAccountLockTime();
					if (loginFailureLockTime == 0) {
						throw new LockedAccountException();
					}
					Date lockedDate = sysUser.getLockedDate();
					Date unlockDate = DateUtils.addMinutes(lockedDate, loginFailureLockTime);
					if (new Date().after(unlockDate)) {
						sysUser.setLoginFailureCount(0);
						sysUser.setIsLocked(false);
						sysUser.setLockedDate(null);
						sysUserService.update(sysUser);
					} else {
						throw new LockedAccountException();
					}
				} else {
					sysUser.setLoginFailureCount(0);
					sysUser.setIsLocked(false);
					sysUser.setLockedDate(null);
					sysUserService.update(sysUser);
				}
			}
			if (!DigestUtils.md5Hex(password).equals(sysUser.getPassword())) {
				int loginFailureCount = sysUser.getLoginFailureCount() + 1;
				if (loginFailureCount >= setting.getAccountLockCount()) {
					sysUser.setIsLocked(true);
					sysUser.setLockedDate(new Date());
				}
				sysUser.setLoginFailureCount(loginFailureCount);
				sysUserService.update(sysUser);
				throw new IncorrectCredentialsException();
			}
			sysUser.setLoginIp(ip);
			sysUser.setLoginDate(new Date());
			sysUser.setLoginFailureCount(0);					
			sysUserService.update(sysUser);			
			SimpleAuthenticationInfo simpleAuthenticationInfo= new SimpleAuthenticationInfo(new Principal(sysUser.getId(), username), password, getName());
			return simpleAuthenticationInfo;
		}
		throw new UnknownAccountException();
	}

	/**
	 * 获取授权信息
	 * 
	 * @param principals
	 *            principals
	 * @return 授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserService sysUserService=(SysUserService)SpringUtils.getBean("sysUserServiceImpl");		
		Principal principal = (Principal) principals.fromRealm(getName()).iterator().next();
		
		return null;
	}

}