package com.assignment.model;

public class Promotion {
    private String offer;
    private double price;

    public Promotion(String offer, double price) {
        this.offer = offer;
        this.price = price;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "offer='" + offer + '\'' +
                ", price=" + price +
                '}';
    }
}
