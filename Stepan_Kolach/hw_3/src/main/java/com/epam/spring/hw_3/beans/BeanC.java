package com.epam.spring.hw_3.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"beanB"})
public class BeanC extends BaseBean{
    
    @Value("${BeanC.name}")
    private String name;
    @Value("${BeanC.value}")
    private String value;

    public BeanC(String name, Long value) {
        super(name, value);
    }

    public void init(){
        System.out.println("In Init method of beabC");
    }

    public void destroy(){
        System.out.println("In destroy method of beanC");
    }
}