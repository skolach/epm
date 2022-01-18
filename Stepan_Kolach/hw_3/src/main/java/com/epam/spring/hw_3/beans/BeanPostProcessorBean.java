package com.epam.spring.hw_3.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessorBean implements BeanPostProcessor{

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("+++ postProcessAfterInitialization() +++");
		if (bean instanceof BaseBean) {
            System.out.println(beanName + " is valid: " + ((BaseBean)bean).validate());
        }
        return bean;
	}

}