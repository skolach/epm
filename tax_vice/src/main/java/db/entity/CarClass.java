package db.entity;

import java.io.Serializable;

public class CarClass implements Serializable{
    // id INT NOT NULL AUTO_INCREMENT,
    // `name` VARCHAR(10) NOT NULL,
    // `rate` SMALLINT NOT NULL,
    // `description` VARCHAR(100),
    private int id;
    private String name;
    private int rate;
    private String description;
    
    public CarClass() {
    }

    public CarClass(int id, String name, int rate, String description) {
        this.id = id;
        this.name = name;
        this.rate = rate;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarClass [description=" + description + ", id=" + id +
            ", name=" + name + ", rate=" + rate + "]";
    }   
}