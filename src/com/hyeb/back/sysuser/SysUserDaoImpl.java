/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.sysuser;

import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;

import com.hyeb.entity.SysUser;
import com.hyeb.back.sysuser.SysUserDao;
import com.hyeb.dao.BaseDaoImpl;

import org.springframework.stereotype.Repository;

/**
 * Dao - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("sysUserDaoImpl")
public class SysUserDaoImpl extends BaseDaoImpl<SysUser, Long> implements SysUserDao {

	public boolean usernameExists(String username) {
		if (username == null) {
			return false;
		}
		String jpql = "select count(*) from SysUser sysUser where lower(sysUser.username) = lower(:username)";
		Long count = entityManager.createQuery(jpql, Long.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
		return count > 0;
	}

	public SysUser findByUsername(String username) {
		SysUser sysUser=null;
		if (username != null) {
			try {
				String jpql = "select sysUser from SysUser sysUser where lower(sysUser.username) = lower(:username)";
				sysUser=entityManager.createQuery(jpql, SysUser.class).setFlushMode(FlushModeType.COMMIT).setParameter("username", username).getSingleResult();
				
			} catch (NoResultException e) {
				return null;
			}
		}		
		return sysUser;
	}

}