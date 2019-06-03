package com.wjy329;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@ServletComponentScan
@MapperScan({"com.wjy329.wshiro.dao"})
public class WWebApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(WWebApplication.class);
	}


	@Configuration
	public class DefaultView implements  WebMvcConfigurer {

		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			// 设定首页为index
			registry.addViewController("/").setViewName("forward:/index");

			// 设定匹配的优先级
			registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

		}
	}

	public static void main(String[] args) {
		SpringApplication.run(WWebApplication.class, args);
	}



}

