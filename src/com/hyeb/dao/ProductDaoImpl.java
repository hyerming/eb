/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.hyeb.Filter;
import com.hyeb.Order;
import com.hyeb.Page;
import com.hyeb.Pageable;
import com.hyeb.Setting;
import com.hyeb.entity.Product;
import com.hyeb.entity.Product.OrderType;
import com.hyeb.util.SettingUtils;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

/**
 * Dao - 商品
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("productDaoImpl")
public class ProductDaoImpl extends BaseDaoImpl<Product, Long> implements ProductDao {

	@Override
	public boolean snExists(String sn) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Product findBySn(String sn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> search(String keyword, Boolean isGift, Integer count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> findSalesList(Date beginDate, Date endDate, Integer count) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}