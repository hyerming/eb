/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.sysuser;

import java.util.HashSet;

import javax.annotation.Resource;

import com.hyeb.Message;
import com.hyeb.Pageable;
import com.hyeb.entity.SysUser;
import com.hyeb.entity.BaseEntity.Save;
import com.hyeb.entity.Role;
import com.hyeb.back.sysuser.SysUserService;
import com.hyeb.back.controller.BaseController;
import com.hyeb.back.role.RoleService;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Controller("backSysUserController")
@RequestMapping("/back/sysuser")
public class SysUserController extends BaseController {

	@Resource(name = "sysUserServiceImpl")
	private SysUserService sysUserService;
	@Resource(name = "roleServiceImpl")
	private RoleService roleService;

	/**
	 * 检查用户名是否存在
	 */
	@RequestMapping(value = "/check_username", method = RequestMethod.GET)
	public @ResponseBody
	boolean checkUsername(String username) {
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (sysUserService.usernameExists(username)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		return "/back/sysuser/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysUser sysUser, Long[] roleIds, RedirectAttributes redirectAttributes) {
		if (!isValid(sysUser, Save.class)) {
			return ERROR_VIEW;
		}
		if (sysUserService.usernameExists(sysUser.getUsername())) {
			return ERROR_VIEW;
		}
		sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword()));
		sysUser.setIsLocked(false);
		sysUser.setLoginFailureCount(0);
		sysUser.setLockedDate(null);
		sysUser.setLoginDate(null);
		sysUser.setLoginIp(null);		
		sysUserService.save(sysUser);
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(Long id, ModelMap model) {
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("admin", sysUserService.find(id));
		return "/back/sysuser/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(SysUser sysUser, RedirectAttributes redirectAttributes) {
		if (!isValid(sysUser)) {
			return ERROR_VIEW;
		}
		SysUser pSysUser = sysUserService.find(sysUser.getId());
		if (pSysUser == null) {
			return ERROR_VIEW;
		}
		if (StringUtils.isNotEmpty(sysUser.getPassword())) {
			sysUser.setPassword(DigestUtils.md5Hex(sysUser.getPassword()));
		} else {
			sysUser.setPassword(pSysUser.getPassword());
		}
		if (pSysUser.getIsLocked() && !sysUser.getIsLocked()) {
			sysUser.setLoginFailureCount(0);
			sysUser.setLockedDate(null);
		} else {
			sysUser.setIsLocked(pSysUser.getIsLocked());
			sysUser.setLoginFailureCount(pSysUser.getLoginFailureCount());
			sysUser.setLockedDate(pSysUser.getLockedDate());
		}
		sysUserService.update(sysUser, "username", "loginDate", "loginIp", "orders");
		addFlashMessage(redirectAttributes, SUCCESS_MESSAGE);
		return "redirect:list.jhtml";
	}

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Pageable pageable, ModelMap model) {
		model.addAttribute("page", sysUserService.findPage(pageable));
		return "/back/sysuser/list";
	}

	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Long[] ids) {
		if (ids.length >= sysUserService.count()) {
			return Message.error("admin.common.deleteAllNotAllowed");
		}
		sysUserService.delete(ids);
		return SUCCESS_MESSAGE;
	}

}