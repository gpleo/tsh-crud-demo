package com.gopersist.demo.tsh.thrift;

import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;

public class ThriftServer {
	@Autowired
	private Blogs blogs;
	
	private int port;
	public ThriftServer(int port) {
		this.port = port;
	}
	
	public void start() {
		final int port = this.port;
		new Thread() {
			@Override
			public void run() {
				try {
					// 传输通道 - 非阻塞方式
					TNonblockingServerSocket serverTransport = new TNonblockingServerSocket(port);
					
					// 多线程半同步半异步
					TThreadedSelectorServer.Args tArgs = new TThreadedSelectorServer.Args(serverTransport);
	
					// 注册多个处理类
					TProcessor blogProcessor = new com.gopersist.demo.tsh.Blogs.Processor<com.gopersist.demo.tsh.Blogs.Iface>(blogs);
					TMultiplexedProcessor processor = new TMultiplexedProcessor();
					processor.registerProcessor("blogs", blogProcessor);
					tArgs.processor(processor);
					
					// 使用一个带Buffer的Socket进行IO传输，使用NoblockingServer 的时候会需要使用TFramedTransport
					tArgs.transportFactory(new TFramedTransport.Factory());
					
					//二进制协议
					tArgs.protocolFactory(new TBinaryProtocol.Factory());
					
					// 多线程半同步半异步的服务模型
					TServer server = new TThreadedSelectorServer(tArgs);
					System.out.println("Server started.");
					server.serve(); // 启动服务
				} catch (TTransportException tte) {
					System.out.println("Thrift server started failed.");
					tte.printStackTrace();
				}
			}
		}.start();
	}
}
