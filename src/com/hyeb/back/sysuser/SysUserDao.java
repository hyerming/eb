/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.sysuser;

import com.hyeb.entity.SysUser;
import com.hyeb.dao.BaseDao;

/**
 * Dao - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface SysUserDao extends BaseDao<SysUser, Long> {

	/**
	 * 判断用户名是否存在
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 用户名是否存在
	 */
	boolean usernameExists(String username);

	/**
	 * 根据用户名查找管理员
	 * 
	 * @param username
	 *            用户名(忽略大小写)
	 * @return 管理员，若不存在则返回null
	 */
	SysUser findByUsername(String username);

}