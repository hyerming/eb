/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.back.log;

import com.hyeb.entity.Log;
import com.hyeb.service.BaseService;

/**
 * Service - 日志
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public interface LogService extends BaseService<Log, Long> {

	/**
	 * 清空日志
	 */
	void clear();

}