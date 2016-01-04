package com.gopersist.demo.tsh.thrift;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gopersist.demo.tsh.Blog;

public class BlogClient {
	private static TTransport transport;
	private static com.gopersist.demo.tsh.Blogs.Client blogClient;
	
	@BeforeClass
	public static void init() throws TException {
		 transport = new TSocket("localhost", 9999);
		 transport = new TFastFramedTransport(transport);
		 transport.open();
		 
		// 设置传输协议为二进制压缩格式
        TProtocol protocol = new TCompactProtocol(transport);
        TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol, "blogs");
        blogClient = new com.gopersist.demo.tsh.Blogs.Client(multiProtocol);
        
        blogClient.create(new Blog(0, "blog1", "blog content 1"));
        blogClient.create(new Blog(0, "blog2", "blog content 2"));
        
        
        // create
//        Blog blog = new Blog();
//        blog.setId("12");
//        try {
//       	 client.create(blog);
//        } catch(RequestException re) {
//       	 System.out.println(re.errorCode + ", " + re.errorMessage + ", errorFields: " + re.errorFields.toString());
//        }
	}
	
	@AfterClass
	public static void close() {
		transport.close();
	}
	
	@Test
	public void testList() throws TException {
		List<Blog> blogs = blogClient.listAll();
		for (Blog b : blogs) {
			System.out.println(b.getId() + ", " + b.getBlogName() + ", " + b.getContent() + ", " + b.getAuthor());
		}
	}
}
