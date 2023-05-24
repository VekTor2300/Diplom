package com.example.individualpr.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {//здесь обрабатывается страница логина по умолчанию спринг секюрити

    public void addViewControllers(ViewControllerRegistry registry) {//добавление представления для регистрации
        registry.addViewController("/login").setViewName("login");
    }
}
