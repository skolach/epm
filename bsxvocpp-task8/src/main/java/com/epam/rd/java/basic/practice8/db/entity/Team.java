package com.epam.rd.java.basic.practice8.db.entity;

import java.util.Objects;

public class Team {
    
    private long id;
    private String name;

    @Override
    public String toString(){
        return name;
    }

    public long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o){
        
        Objects.requireNonNull(o);

        if (o instanceof Team) {
            return this.name.equals(((Team)o).toString());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return name.hashCode();
    }
    public static Team createTeam(String name) {
        Team team = new Team();
        team.setName(name);
        return team;
    }

}