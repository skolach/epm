package com.epam.rd.java.basic.practice8.db.entity;

import java.util.Objects;

public class User {

    private long id;
    private String login;

    public String getLogin(){
        return login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return login;
    }

    @Override
    public boolean equals(Object o){

        Objects.requireNonNull(o);

        if (o instanceof User) {
            return this.login.equals(((User)o).toString());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return login.hashCode();
    }

    public static User createUser(String login) {
        User user = new User();
        user.setLogin(login);
        return user;
    }

}