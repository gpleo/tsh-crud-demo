package com.gopersist.demo.tsh.service;

import java.util.List;

import com.gopersist.demo.tsh.Blog;

public interface BlogService {
	List<Blog> list();
	Blog create(Blog blog);
	Blog update(Blog blog);
	void delete(Blog blog);
}
