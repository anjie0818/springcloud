package com.example.springcloudconsumerhystrixdashboard.controller;

import com.example.springcloudconsumerhystrixdashboard.feign.HelloRemote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 anjie
 * @创建时间 2018/9/18
 * @描述
 */
@RestController
public class ConsumerController  {
    @Autowired
    HelloRemote helloRemote;
    @RequestMapping("/hello/{name}")
    public String index(@PathVariable("name") String name) {
        return helloRemote.hello(name);
    }
}
