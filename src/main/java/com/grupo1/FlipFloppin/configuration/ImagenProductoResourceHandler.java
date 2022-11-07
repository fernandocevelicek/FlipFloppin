package com.grupo1.FlipFloppin.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ImagenProductoResourceHandler implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/imagenes/**").addResourceLocations("file:///D:/Facultad/TercerSemestre/Programacion/Flip-Floppin/FlipFloppin/imagenes/");
    }
}