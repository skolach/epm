package com.epam.spring.hw_3;

import com.epam.spring.hw_3.config.BeansConfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App 
{
    public static void main( String[] args ){
        System.out.println("");
        System.out.println("*** APPLICATION STARTED ***");

        ApplicationContext context = new AnnotationConfigApplicationContext(BeansConfig.class);

        for (String s : context.getBeanDefinitionNames()) {
            System.out.println("--- " + s);
        }

        ((ConfigurableApplicationContext)context).close();
    }
}