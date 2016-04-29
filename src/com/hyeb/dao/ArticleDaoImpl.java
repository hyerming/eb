/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import com.hyeb.Filter;
import com.hyeb.Order;
import com.hyeb.Page;
import com.hyeb.Pageable;
import com.hyeb.entity.Article;
import com.hyeb.entity.ArticleCategory;
import com.hyeb.entity.Tag;

import org.springframework.stereotype.Repository;

/**
 * Dao - 文章
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
@Repository("articleDaoImpl")
public class ArticleDaoImpl extends BaseDaoImpl<Article, Long> implements ArticleDao {

	@Override
	public List<Article> findList(ArticleCategory articleCategory, List<Tag> tags, Integer count, List<Filter> filters,
			List<Order> orders) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Article> findList(ArticleCategory articleCategory, Date beginDate, Date endDate, Integer first,
			Integer count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Article> findPage(ArticleCategory articleCategory, List<Tag> tags, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	
}