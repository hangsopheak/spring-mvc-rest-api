package com.rupp.spring.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rupp.spring.security.BasicSecurityInterceptor;

@EnableWebMvc //mvc:annotation-driven
@Configuration
@ComponentScan(value = {"com.rupp.spring.controller", "com.rupp.spring.service", "com.rupp.spring.dao"})
public class MvcConfig extends WebMvcConfigurerAdapter {
    
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        SkipNullObjectMapper skipNullMapper = new SkipNullObjectMapper();
        skipNullMapper.init();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setObjectMapper(skipNullMapper);
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        skipNullMapper.setDateFormat(formatter);
        
        converters.add(converter);
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(basicSecurityInterceptor()).addPathPatterns("/**");
    }     
    
    @Bean
    public BasicSecurityInterceptor basicSecurityInterceptor() {
        return new BasicSecurityInterceptor();
    }
}
