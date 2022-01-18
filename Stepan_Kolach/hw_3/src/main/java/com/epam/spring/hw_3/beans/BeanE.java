package com.epam.spring.hw_3.beans;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class BeanE {
    
    private String name;
    private String value;

    @PostConstruct
    public void postConstructOfBeanF(){
        System.out.println("in postConstructOfBeanF() annotated as @PostConstruct");
    }

    @PreDestroy
    public void preDestroyOfBeanF(){
        System.out.println("in preDestroyOfBeanF() annotated as @PreDestroy");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BeanE [name=" + name + ", value=" + value + "]";
    }

}