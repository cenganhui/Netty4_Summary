# Netty 4

## Chapter2 AIO 初体验（2020-07-19）

基于 AIO 的一段简单代码，感受一下服务端和客户端的交互过程，同时也体验一下 API 的使用。

- AIOClient：AIO 客户端
- AIOServer：AIO 服务端

## Chapter4 基于 Netty 手写 Tomcat Demo（2020-07-19）

- bio.tomcat：基于传统 I/O 手写 Tomcat Demo
   - 浏览器输入 http://localhost:8081/firstServlet.do
   - 浏览器输入 http://localhost:8081/secondServlet.do

- netty.tomcat：基于 Netty 重构 Tomcat Demo
   - 浏览器输入 http://localhost:8081/firstServlet.do
   - 浏览器输入 http://localhost:8081/secondServlet.do