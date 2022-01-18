package db.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Proposal implements Serializable{
    private int id;
    private int orderId;
    private int proposalId;
    private String carNumber;
    private java.sql.Time timeToWait;
    private BigDecimal price;
    private String description;

    public Proposal() {}
    
    public Proposal(int id, int orderId, int proposalId, String carNumber,
    java.sql.Time timeToWait, BigDecimal price, String description) {
        this.id = id;
        this.orderId = orderId;
        this.proposalId = proposalId;
        this.carNumber = carNumber;
        this.timeToWait = timeToWait;
        this.price = price;
        this.description = description;
    }
    
    public String getCarNumber() {
        return carNumber;
    }
    
    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getOrderId() {
        return orderId;
    }
    
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    
    public int getProposalId() {
        return proposalId;
    }
    
    public void setProposalId(int proposalId) {
        this.proposalId = proposalId;
    }
    
    public java.sql.Time getTimeToWait() {
        return timeToWait;
    }
    
    public void setTimeToWait(java.sql.Time timeToWait) {
        this.timeToWait = timeToWait;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Proposal [carNumber=" + carNumber + ", description=" + description + ", id=" + id + ", orderId="
        + orderId + ", price=" + price + ", proposalId=" + proposalId + ", timeToWait=" + timeToWait + "]";
    }
}