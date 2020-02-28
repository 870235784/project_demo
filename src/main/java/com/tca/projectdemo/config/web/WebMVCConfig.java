package com.tca.projectdemo.config.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 相当于springmvc.xml文件 
 * 	1.继承WebMvcConfigurationSupport
 * 	2.添加@Configuration标签
 * @author zhouan
 *
 */
@Configuration
public class WebMVCConfig extends WebMvcConfigurationSupport{

	@Autowired
	private WebInterceptor webInterceptor;

	@Value("${path.pic}")
	private String picPath;

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(webInterceptor)
				.addPathPatterns("/**") // 拦截/**开始的请求
				.excludePathPatterns("/account/login"); //不拦截/account/login的请求
		super.addInterceptors(registry);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/account/pic/**")
				.addResourceLocations("file:" + System.getProperty("user.dir") + picPath);
		super.addResourceHandlers(registry);
	}
}
