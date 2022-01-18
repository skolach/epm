package com.epam.taxvicespring.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class OrderEnhanced extends Order{

    private String routes;
    private String login;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoutes() {
        return routes;
    }

    public void setRoutes(String routes) {
        this.routes = routes;
    }

    public OrderEnhanced(int id, int userId, Timestamp createdAt, Timestamp startAt,
    Timestamp endAt, BigDecimal price, int routeDiscount, int userDiscount,
    BigDecimal cash, String routes, String login) {
        super(id, userId, createdAt, startAt, endAt, price, routeDiscount, userDiscount, cash);
        this.routes = routes;
        this.login = login;
    }

    @Override
    public String toString() {
        return "OrderEnhanced [routes=" + routes + "login=" + login + "] " + super.toString();
    }
}