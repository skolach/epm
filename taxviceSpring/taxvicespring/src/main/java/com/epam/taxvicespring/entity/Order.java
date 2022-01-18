package com.epam.taxvicespring.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order implements Serializable {

    private int id;
    private int userId;
    private Timestamp createdAt;
    private Timestamp startAt;
    private Timestamp endAt;
    private BigDecimal price;
    private int routeDiscount;
    private int userDiscount;
    private BigDecimal cash;
    
    public Order() {
    }
    
    public Order(int id, int userId, Timestamp createdAt, Timestamp startAt, Timestamp endAt, BigDecimal price, // NOSONAR
            int routeDiscount, int userDiscount, BigDecimal cash) {
        this.id = id;
        this.userId = userId;
        this.createdAt = createdAt;
        this.startAt = startAt;
        this.endAt = endAt;
        this.price = price;
        this.routeDiscount = routeDiscount;
        this.userDiscount = userDiscount;
        this.cash = cash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getEndAt() {
        return endAt;
    }

    public void setEndAt(Timestamp endAt) {
        this.endAt = endAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getRouteDiscount() {
        return routeDiscount;
    }

    public void setRouteDiscount(int routeDiscount) {
        this.routeDiscount = routeDiscount;
    }

    public int getUserDiscount() {
        return userDiscount;
    }

    public void setUserDiscount(int userDiscount) {
        this.userDiscount = userDiscount;
    }

    public BigDecimal getCash() {
        return cash;
    }

    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }


    @Override
    public String toString() {
        return "Order [cash=" + cash + ", createdAt=" + createdAt + ", endAt=" + endAt + ", id=" + id + ", price="
                + price + ", routeDiscount=" + routeDiscount + ", startAt=" + startAt + ", userDiscount=" + userDiscount
                + ", userId=" + userId + "]";
    }

}