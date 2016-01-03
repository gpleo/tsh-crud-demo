package com.gopersist.demo.tsh.dao;

import java.util.List;

import com.gopersist.demo.tsh.Blog;

public interface BlogDao {
	List<Blog> list();
	Blog create(Blog blog);
	Blog update(Blog blog);
	void delete(Blog blog);
}
