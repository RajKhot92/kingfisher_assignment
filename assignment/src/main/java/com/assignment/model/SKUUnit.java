package com.assignment.model;

public class SKUUnit {
    private String skuId;
    private double price;
    private int qty=0;
    private boolean promotionApplied = false;

    public SKUUnit(String skuId, double price) {
        this.skuId = skuId;
        this.price = price;
    }

    public SKUUnit(String skuId, int qty) {
        this.skuId = skuId;
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

    public boolean isPromotionApplied() {
        return promotionApplied;
    }

    public void setPromotionApplied(boolean promotionApplied) {
        this.promotionApplied = promotionApplied;
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
