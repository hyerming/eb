
package com.hyeb.back.sysuser;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import com.hyeb.Principal;
import com.hyeb.entity.SysUser;
import com.hyeb.service.BaseServiceImpl;
import com.hyeb.entity.Role;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service - 管理员
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Service("sysUserServiceImpl")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Long> implements SysUserService {

	@Resource(name = "sysUserDaoImpl")
	private SysUserDao sysUserDao;

	@Resource(name = "sysUserDaoImpl")
	public void setBaseDao(SysUserDao sysUserDao) {
		super.setBaseDao(sysUserDao);
	}

	@Transactional(readOnly = true)
	public boolean usernameExists(String username) {
		return sysUserDao.usernameExists(username);
	}

	@Transactional(readOnly = true)
	public SysUser findByUsername(String username) {
		SysUser sysUser=sysUserDao.findByUsername(username);
		return sysUser;
	}

	@Transactional(readOnly = true)
	public boolean isAuthenticated() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			return subject.isAuthenticated();
		}
		return false;
	}

	@Transactional(readOnly = true)
	public SysUser getCurrent() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return sysUserDao.find(principal.getId());
			}
		}
		return null;
	}

	@Transactional(readOnly = true)
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void save(SysUser sysUser) {
		super.save(sysUser);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public SysUser update(SysUser sysUser) {
		return super.update(sysUser);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public SysUser update(SysUser sysUser, String... ignoreProperties) {
		return super.update(sysUser, ignoreProperties);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long id) {
		super.delete(id);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(Long... ids) {
		super.delete(ids);
	}

	@Override
	@Transactional
	@CacheEvict(value = "authorization", allEntries = true)
	public void delete(SysUser sysUser) {
		super.delete(sysUser);
	}

}