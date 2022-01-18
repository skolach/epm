package db.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import security.SecurityConfig;

public class User implements Serializable{

    private int id;
    private boolean isAdmin;
    private String login;
    private String password;
    private String name;
    private Integer discount;
    private List<String> roles = new ArrayList<>(1);
    
    public User() {
    }

    public User(boolean isAdmin, String login, String password, String name, Integer discount) {
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
        this.roles.add(isAdmin ? SecurityConfig.ROLE_ADMIN : SecurityConfig.ROLE_CUSTOMER);
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

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public List<String> getRoles(){
        return roles;
    }

    @Override
    public String toString(){
        return "[id=" + this.id + ", is_admin=" + this.isAdmin + ", login=" +
            this.login + ", password=" + this.password + ", name=" + this.name +
            ", discount=" + this.discount + "]";
    }
}