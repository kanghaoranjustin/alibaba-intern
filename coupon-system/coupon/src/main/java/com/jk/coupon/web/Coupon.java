package com.jk.coupon.web;

import java.util.Date;

public class Coupon {
    private String name;
    private int num;
    private String type;
    private String instruction;
    private String detail;
    private String start_date;
    private String end_date;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getDetail() {
        return detail;
    }

    public String getInstruction() {
        return instruction;
    }

    public String getType() {
        return type;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

}
