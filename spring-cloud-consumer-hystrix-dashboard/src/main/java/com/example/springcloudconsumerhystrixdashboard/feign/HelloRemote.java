package com.example.springcloudconsumerhystrixdashboard.feign;

import com.example.springcloudconsumerhystrixdashboard.Hystrix.HelloRemoteHystrix;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @创建人 anjie
 * @创建时间 2018/9/18
 * @描述
 */
@FeignClient(name= "spring-cloud-producer",fallback = HelloRemoteHystrix.class)
//在服务熔断的时候返回fallback类中的内容
public interface HelloRemote {
    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name") String name);


}
