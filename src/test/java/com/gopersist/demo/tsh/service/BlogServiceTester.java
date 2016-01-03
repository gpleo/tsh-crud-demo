package com.gopersist.demo.tsh.service;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gopersist.demo.tsh.Blog;

public class BlogServiceTester {
	private static ApplicationContext ctx;
	private static BlogService blogService;
	
	@BeforeClass
	public static void init() {
		ctx = new ClassPathXmlApplicationContext(
				"applicationContext-hibernate.xml", 
				"applicationContext-data-source.xml", 
				"applicationContext-dao.xml", 
				"applicationContext-transaction.xml",
				"applicationContext-service.xml");
		blogService = ctx.getBean(BlogService.class);
		
		blogService.create(new Blog(0, "blog1", "blog content 1"));
		blogService.create(new Blog(0, "blog2", "blog content 2"));
	}
	
	@Test
	public void testList() {
		List<Blog> blogs = blogService.list();
		for (Blog b : blogs) {
			System.out.println(b.getId());
		}
	}
}
