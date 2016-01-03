使用下面的命令使用Thrift生成相关类：

thrift --gen java -o src/main idl/blog.thrift

测试步骤：

1. 运行 src/test/java下的com.gopersist.demo.tsh.thrift.ServerStart启动Thrift服务。
2. JUnit运行 src/test/java 下的com.gopersist.demo.tsh.thrift.BlogClient来调用Thrift服务测试。
