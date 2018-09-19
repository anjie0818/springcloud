package com.example.springcloudconsumerhystrixdashboard.Hystrix;

import com.example.springcloudconsumerhystrixdashboard.feign.HelloRemote;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @创建人 anjie
 * @创建时间 2018/9/18
 * @描述
 */
@Component
public class HelloRemoteHystrix implements HelloRemote {
    @Override
    public String hello(@RequestParam(value = "name") String name) {
        return "hello" +name+", this messge send failed ";
    }
}
