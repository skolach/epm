package com.epam.spring.hw_3.config;

import com.epam.spring.hw_3.beans.BeanA;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OtherConfig {
    @Bean(name = "beanA")
    public BeanA getBeanA(@Value("beanA") String name, @Value("100") Long value){
        return new BeanA(name, value);
    }

}