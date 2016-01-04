package com.gopersist.demo.tsh.thrift;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerStarter {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext-hibernate.xml", 
				"applicationContext-data-source.xml", 
				"applicationContext-dao.xml", 
				"applicationContext-transaction.xml",
				"applicationContext-service.xml",
				"applicationContext-validation.xml",
				"applicationContext-thrift.xml");
		
		ThriftServer thriftServer = ctx.getBean(ThriftServer.class);
		thriftServer.start();
	}
}
