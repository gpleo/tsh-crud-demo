package com.gopersist.demo.tsh.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gopersist.demo.tsh.Blog;
import com.gopersist.demo.tsh.dao.BlogDao;

@Repository
public class BlogDaoImpl implements BlogDao {
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Blog> list() {
		String hql = "from " + Blog.class.getName();
		Query query = this.getSession().createQuery(hql);
		return query.list();
	}

	@Override
	public Blog create(Blog blog) {
		this.getSession().save(blog);
		return blog;
	}

	@Override
	public Blog update(Blog blog) {
		this.getSession().update(blog);
		return blog;
	}

	@Override
	public void delete(Blog blog) {
		this.getSession().delete(blog);
	}
}
