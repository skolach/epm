package utils;

import java.io.Serializable;

public class SortField implements Serializable{

    private String fieldName;
    private String order;
    private String chCode;

    public SortField() {
    }

    public SortField(String fieldName, String order) {
        this.fieldName = fieldName;
        this.order = order;
        this.chCode = "asc".equals(order) ? "&#x25b4" : "&#x25be";
    }

    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
        this.chCode = "asc".equals(order) ? "&#x25b4" : "&#x25be";
    }

    public String getChCode() {
        return chCode;
    }

    public void setChCode(String chCode) {
        this.chCode = chCode;
    }

    @Override
    public String toString() {
        return "SortField [fieldName=" + fieldName + ", order=" + order + "]";
    }

    public String toSQLString(){
        return fieldName + " " + order + " ";
    }

    public void reverseOrder() {
        this.order = this.order.equals("asc") ? "desc" : "asc";
        this.chCode = "asc".equals(order) ? "&#x25b4" : "&#x25be";
    }
}