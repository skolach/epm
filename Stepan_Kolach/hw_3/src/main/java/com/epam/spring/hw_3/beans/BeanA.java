package com.epam.spring.hw_3.beans;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class BeanA extends BaseBean implements InitializingBean, DisposableBean {

    public BeanA(String name, Long value) {
        super(name, value);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("In destroy() method of DisposableBean interface of beanA");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("In afterPropertiesSet() method of InitializingBean interface of beanA");
    }

}