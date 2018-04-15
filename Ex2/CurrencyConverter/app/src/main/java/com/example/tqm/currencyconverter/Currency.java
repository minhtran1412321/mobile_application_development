package com.example.tqm.currencyconverter;

public class Currency {
    private String code;
    private double price;

    public Currency(String code, int price) {
        this.code = code;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public String getCode() {
        return code;
    }
}
