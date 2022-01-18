package com.epam.spring.hw_3.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanD extends BaseBean{

    @Value("${BeanD.name}")
    private String name;
    @Value("${BeanD.value}")
    private String value;

    public BeanD(String name, Long value) {
        super(name, value);
    }

    public void init(){
        System.out.println("In Init method of beabD");
    }

    public void destroy(){
        System.out.println("In destroy method of beanD");
    }
}