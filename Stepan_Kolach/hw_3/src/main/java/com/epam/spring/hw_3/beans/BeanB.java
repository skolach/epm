package com.epam.spring.hw_3.beans;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn({"beanD"})
public class BeanB extends BaseBean{

    public BeanB(String name, Long value) {
        super(name, value);
    }

    public void newInitMethodForBeanB(){
        System.out.println("10. In newInitMethodForBeanB() method of beabB");
    }

    public void init(){
        System.out.println("In Init method of beabB");
    }

    public void destroy(){
        System.out.println("In destroy method of beanB");
    }
}