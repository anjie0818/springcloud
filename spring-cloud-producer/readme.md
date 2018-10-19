## 服务提供
我们假设服务提供者有一个hello方法，可以根据传入的参数，提供输出“hello xxx，this is first messge”的服务
### 实例
#### 1.配置pom文件
```xml
	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
```
#### 2.配置properties文件
```properties
spring.application.name=spring-cloud-producer
server.port=9003
#与注册中心交互地址
eureka.client.serviceUrl.defaultZone=http://peer1:8000/eureka/
```
#### 3.启动类
启动类中添加@EnableDiscoveryClient注解
#### 4.创建controller
### 负载均衡
properties文件中修改端口，再次打包启动，完成负载


