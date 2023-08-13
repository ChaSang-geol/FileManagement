package com.sk.filemanagement.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Webconfig implements WebMvcConfigurer {
  public static final String ALLOWED_METHOD_NAMES = "GET,POST,PUT,DELETE,PATCH";

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry.addMapping("/**")
      .allowedOrigins("*")
      .allowedMethods(ALLOWED_METHOD_NAMES.split(","));
  }
}
