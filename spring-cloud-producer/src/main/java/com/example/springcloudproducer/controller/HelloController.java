package com.example.springcloudproducer.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @创建人 anjie
 * @创建时间 2018/9/18
 * @描述
 */
@RestController
public class HelloController {
    /**
     *此接口直接访问此项目可以调用，也可以让消费者通过FeignClient来进行调用
     * @param name
     * @return
     */
    @RequestMapping("/hello")
    public String index(@RequestParam String name) {
        return "hello "+name+"，this is first messge";
    }
}
