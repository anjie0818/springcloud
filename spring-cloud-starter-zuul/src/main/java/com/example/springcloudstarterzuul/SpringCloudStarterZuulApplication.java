package com.example.springcloudstarterzuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class SpringCloudStarterZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStarterZuulApplication.class, args);
	}
}
