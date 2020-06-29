package com.jk.coupon.web;

public class Good {
    private String name;
    private String type;
    private int number;
    private String Status;
    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getNumber() {
        return number;
    }

    public String getStatus() {
        return Status;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStatus(String status) {
        Status = status;
    }


}
