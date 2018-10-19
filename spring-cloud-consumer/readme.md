## 服务调用
### 实例
#### 1.配置pom文件
```xml
	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-eureka</artifactId>
	</dependency>
	<dependency>
    			<groupId>org.springframework.cloud</groupId>
    			<artifactId>spring-cloud-starter-feign</artifactId>
    </dependency>
```
#### 2.配置properties文件
application.properties配置如下：
```
spring.application.name=spring-cloud-consumer
server.port=9001
eureka.client.serviceUrl.defaultZone=http://localhost:8000/eureka/
```
#### 3.启动类
启动类添加@EnableDiscoveryClient和@EnableFeignClients注解。
>Feign是一个声明式Web Service客户端。使用Feign能让编写Web Service客户端更加简单, 它的使用方法是定义一个接口，然后在上面添加注解，同时也支持JAX-RS标准的注解。Feign也支持可拔插式的编码器和解码器。Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。Feign可以与Eureka和Ribbon组合使用以支持负载均衡。
#### 4.feign调用实现
```java
/*
 * name:远程服务名，及spring.application.name配置的名称
 * 此类中的方法和远程服务中contoller中的方法名和参数需保持一致。 
 */
@FeignClient(name= "spring-cloud-producer")
public interface HelloRemote {
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);
}
```
#### 5.web层调用远程服务
```java
//将HelloRemote注入到controller层，像普通方法一样去调用即可。
@RestController
public class ConsumerController {

    @Autowired
    HelloRemote HelloRemote;
	
    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return HelloRemote.hello(name);
    }
}
```
## 熔断器Hystrix
### 特性
#### 1.断路器机制
断路器很好理解, 当Hystrix Command请求后端服务失败数量超过一定比例(默认50%), 断路器会切换到开路状态(Open). 这时所有请求会直接失败而不会发送到后端服务. 断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN). 这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 否则重新切换到开路状态(OPEN). Hystrix的断路器就像我们家庭电路中的保险丝, 一旦后端服务不可用, 断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 并且断路器有*自我检测并恢复的能力*.
#### 2.Fallback
Fallback相当于是降级操作. 对于查询操作, 我们可以实现一个fallback方法, 当请求后端服务出现异常的时候, 可以使用fallback方法返回的值. fallback方法的返回值一般是*设置的默认值或者来自缓存*.
#### 3.资源隔离
在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池. 例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要优点是运行环境被隔离开了. 这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他服务造成影响. 但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 可以使用Hystrix的信号模式(Semaphores)来隔离资源.
### Feign Hystrix实例
因为熔断*只是作用在服务调用*这一端，因此我们根据上一篇的示例代码只需要改动spring-cloud-consumer项目相关代码就可以。因为，Feign中已经依赖了Hystrix所以在maven配置上不用做任何改动。
#### 1.配置文件
application.properties添加这一条：
```properties
feign.hystrix.enabled=true
```
#### 2.创建回调类
创建HelloRemoteHystrix类继承与HelloRemote实现回调的方法
```java
     @Component
     public class HelloRemoteHystrix implements HelloRemote{
     
         @Override
         public String hello(@RequestParam(value = "name") String name) {
             return "hello" +name+", this messge send failed ";
         }
     }
```   
#### 3.添加fallback属性
在HelloRemote类添加指定fallback类，在服务熔断的时候返回fallback类中的内容。
```java
       @FeignClient(name= "spring-cloud-producer",fallback = HelloRemoteHystrix.class)
       public interface HelloRemote {
       
           @RequestMapping(value = "/hello")
           public String hello(@RequestParam(value = "name") String name);
       
       }
```       


