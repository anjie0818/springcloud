package com.example.springcloudstarterzuul;

import com.example.springcloudstarterzuul.zuulFilter.MyFilter;
import com.fasterxml.jackson.core.filter.TokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
	@SpringBootApplication
	public class SpringCloudStarterZuulApplication {

		public static void main(String[] args) {
			SpringApplication.run(SpringCloudStarterZuulApplication.class, args);
		}
		//将TokenFilter加入到请求拦截队列，在启动类中添加以下代码：
		@Bean
		public MyFilter tokenFilter() {
			return new MyFilter();
		}
}
