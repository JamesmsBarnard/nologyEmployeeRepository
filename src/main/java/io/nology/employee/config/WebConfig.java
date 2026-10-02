package io.nology.employee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class WebConfig implements WebMvcConfigurer{
    public void addCorsMappings(CorsRegistry registry) {

        String[] allowedOrigins = { "http://localhost:5173/", "http://localhost:5174/", "https://employeeappbarnard.lol", "d1mjld5fto0cxx.cloudfront.net"};
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
