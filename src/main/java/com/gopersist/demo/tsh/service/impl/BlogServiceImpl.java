package com.gopersist.demo.tsh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gopersist.demo.tsh.Blog;
import com.gopersist.demo.tsh.dao.BlogDao;
import com.gopersist.demo.tsh.service.BlogService;

@Service
public class BlogServiceImpl implements BlogService {
	@Autowired
	private BlogDao blogDao;

	@Override
	public List<Blog> list() {
		return this.blogDao.list();
	}

	@Override
	public Blog create(Blog blog) {
		return this.blogDao.create(blog);
	}

	@Override
	public Blog update(Blog blog) {
		return this.blogDao.update(blog);
	}

	@Override
	public void delete(Blog blog) {
		this.blogDao.delete(blog);
	}
}
