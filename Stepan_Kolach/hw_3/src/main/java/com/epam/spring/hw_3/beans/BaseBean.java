package com.epam.spring.hw_3.beans;

public class BaseBean {

    private String name;
    private Long value;

    public BaseBean(String name, Long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean validate() {
        return (name != null) && (value != null) && (value > 0);
    }

    public String toString() {
        return "BeanA [name=" + name + ", value=" + value + "]";
    }

}