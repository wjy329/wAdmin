package com.wjy329;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = {"com.wjy329.wshiro.dao"})
public class WWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WWebApplication.class);
	}


	@Configuration
	public class DefaultView extends WebMvcConfigurerAdapter {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			// 设定首页为index
			registry.addViewController("/").setViewName("forward:/index");

			// 设定匹配的优先级
			registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

			// 添加视图控制类
			super.addViewControllers(registry);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WWebApplication.class, args);
	}



}

