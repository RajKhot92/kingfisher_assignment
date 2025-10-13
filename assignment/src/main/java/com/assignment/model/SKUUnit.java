package com.assignment.model;

public class SKUUnit {
    private String skuId;
    private double price;
    private int qty=0;

    public SKUUnit(String skuId, double price) {
        this.skuId = skuId;
        this.price = price;
    }

    public SKUUnit(String skuId, int qty) {
        this.skuId = skuId;
        this.qty = qty;
    }

    public SKUUnit(String skuId, double price, int qty) {
        this.skuId = skuId;
        this.price = price;
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "SKUUnit{" +
                "skuId='" + skuId + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
