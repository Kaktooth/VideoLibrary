package com.projects.videolibrary.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/main");
        registry.addViewController("/main/**").setViewName("./main");
        registry.addViewController("/log-in").setViewName("./log-in");
        registry.addViewController("/admin-dashboard/**").setViewName("./admin-dashboard");
        registry.addRedirectViewController("/admin-dashboard/**","/admin-dashboard");
    }
}
