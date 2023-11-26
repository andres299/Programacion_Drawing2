package com.esliceu.PracticaDrawing2.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class Configuration implements WebMvcConfigurer {
    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/CanvasDraw","/UserDraw","/AllDraw",
                        "/ViewDraw","/ModifyCanvas");
    }
}
