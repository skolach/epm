package com.epam.taxvicespring.entity;

import java.io.Serializable;

public class Executor implements Serializable{

    private int orderId;
    private int carId;
    
    public Executor() {
    }

    public Executor(int orderId, int carId) {
        this.orderId = orderId;
        this.carId = carId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    @Override
    public String toString() {
        return "Executor [carId=" + carId + ", orderId=" + orderId + "]";
    }
}