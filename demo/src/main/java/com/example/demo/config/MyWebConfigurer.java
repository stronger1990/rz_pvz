package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// WebMvcConfigurerAdapter 过期了
@Configuration
public class MyWebConfigurer implements WebMvcConfigurer {
    //图片存放根路径
    @Value("${file.rootPath}")
    private String ROOT_PATH;
    //图片存放根目录下的子目录
    @Value("${file.sonPath}")
    private String SON_PATH;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String filePath = "file:" + ROOT_PATH + SON_PATH;
        //指向外部目录
        registry.addResourceHandler("img//**").addResourceLocations(filePath);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}
