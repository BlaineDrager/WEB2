package com.example.demo.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/"); //.setCachePeriod(60*60*24*365);
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/"); //.setCachePeriod(60*60*24*365);
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/"); //.setCachePeriod(60*60*24*365);
        registry.addResourceHandler("/font/**").addResourceLocations("classpath:/static/font/"); //.setCachePeriod(60*60*24*365); // 프로젝트 내의 스태틱 경로
        registry.addResourceHandler("/imageboard/**").addResourceLocations("file:/imageboard/"); //c드라이브에 있는 imageboard 를 연결 시킬거임 // file 예약어
        // .addResourceLocations("file:/imageboard/") 정적 자원 경로
        // 정적 자원을 셋업했다면 시큐리티 컨피그에 있는 경로도 바꿔줘야함
    }

}
