package com.gopersist.demo.tsh.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gopersist.demo.tsh.Blog;
import com.gopersist.demo.tsh.Blogs.Iface;
import com.gopersist.demo.tsh.RequestException;
import com.gopersist.demo.tsh.service.BlogService;

@Component
public class Blogs implements Iface{
	@Autowired
	private BlogService blogService;
	
	@Override
	public List<Blog> listAll() throws TException {
		return blogService.list();
	}

	@Override
	public Blog create(Blog blog) throws RequestException, TException {
		return blogService.create(blog);
	}

	@Override
	public Blog update(Blog blog) throws RequestException, TException {
		return blogService.update(blog);
	}

	@Override
	public void deleteById(long id) throws RequestException, TException {
		Blog blog = new Blog();
		blog.setId(id);
		blogService.delete(blog);
	}
}
