package com.epam.taxvicespring.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable{

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Integer id;
    private boolean isAdmin;
    private String login;
    private String password;
    private String name;
    private Short discount;
    
    public User() {}

    public User(boolean isAdmin, String login, String password, String name, Short discount) {
        this.isAdmin = isAdmin;
        this.login = login;
        this.password = password;
        this.name = name;
        this.discount = discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getDiscount() {
        return discount;
    }

    public void setDiscount(Short discount) {
        this.discount = discount;
    }

    @Override
    public String toString(){
        return "[id=" + this.id + ", is_admin=" + this.isAdmin + ", login=" +
            this.login + ", password=" + this.password + ", name=" + this.name +
            ", discount=" + this.discount + "]";
    }
}