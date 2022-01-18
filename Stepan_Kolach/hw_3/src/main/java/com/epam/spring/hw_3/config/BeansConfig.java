package com.epam.spring.hw_3.config;

import com.epam.spring.hw_3.beans.BeanB;
import com.epam.spring.hw_3.beans.BeanC;
import com.epam.spring.hw_3.beans.BeanD;
import com.epam.spring.hw_3.beans.BeanFactoryPostProcessorBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.epam.spring.hw_3.beans")
@PropertySource("classpath:BeanValues.properties")
@Import(OtherConfig.class)
public class BeansConfig {

    @Bean(name = "beanB", initMethod = "init", destroyMethod = "destroy")
    public BeanB getBeanB(@Value("${BeanB.name}") String name, @Value("${BeanB.value}") Long value){
        return new BeanB(name, value);
    }

    @Bean(name = "beanC", initMethod = "init", destroyMethod = "destroy")
    public BeanC getBeanC(@Value("${BeanC.name}") String name, @Value("${BeanC.value}") Long value){
        return new BeanC(name, value);
    }

    @Bean(name = "beanD", initMethod = "init", destroyMethod = "destroy")
    public BeanD getBeanD(@Value("${BeanD.name}") String name, @Value("${BeanD.value}") Long value){
        return new BeanD(name, value);
    }

    @Bean
    public static BeanFactoryPostProcessorBean getBeanFactoryPostProcessorBean(){
        return new BeanFactoryPostProcessorBean();
    }
}
