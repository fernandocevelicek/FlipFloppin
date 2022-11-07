package com.grupo1.FlipFloppin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagenProductoResourceHandler implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/**").addResourceLocations("file:///C:/Users/franc/OneDrive/Documents/Tercer Semestre/ProgramacionIII/Proyecto Final/FlipFloppin/imagenes/");
    }
}