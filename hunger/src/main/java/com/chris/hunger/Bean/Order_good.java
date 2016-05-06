package com.chris.hunger.Bean;

import java.io.Serializable;

public class Order_good implements Serializable{
    private String  goodname;
    private Integer goodcount;
    private Double goodprice;

    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public Double getGoodprice() {
        return goodprice;
    }

    public void setGoodprice(Double goodprice) {
        this.goodprice = goodprice;
    }

    public Integer getGoodcount() {
        return goodcount;
    }

    public void setGoodcount(Integer goodcount) {
        this.goodcount = goodcount;
    }
}