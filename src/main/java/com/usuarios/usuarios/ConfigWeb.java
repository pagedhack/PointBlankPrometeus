package com.usuarios.usuarios;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ConfigWeb implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registro) {
        registro.addMapping("/*")
        .allowedOriginPatterns("https://cafe-soap-production.up.railway.app","/*", "*")
        .allowedMethods("GET", "POST")
        .allowCredentials(true)
        .allowedHeaders("Access-Control-Allow-Origin", "*", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods");
    }
}
