package com.assignment.job;

import com.assignment.model.Promotion;
import com.assignment.model.SKUUnit;

import java.util.*;
import java.util.logging.Logger;

public class PromotionEngine {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final List<String> listSKU = Arrays.asList("A","B","C","D");
    private final List<SKUUnit> listSKUUnit = new ArrayList<>();
    private final List<Promotion> listPromotion = new ArrayList<>();

    private final List<SKUUnit> cart = new ArrayList<>();

    public void initialize() throws Exception {
        initializeSKUPrice();
        initializePromotion();
        initializeCart();
        processCart();
    }

    public void addToCart(String skuId, int qty) throws Exception {
        if(isValidSKUId(skuId) && isValidQty(qty)){
            cart.add(new SKUUnit(skuId, qty));
        }
    }

    public SKUUnit updateCart(SKUUnit skuUnit) throws Exception {
        Optional<SKUUnit> existUnit = listSKUUnit.stream().distinct()
                .filter(x-> x.getSkuId().equals(skuUnit.getSkuId()))
                .findAny();
        if(existUnit.isPresent()){
            if(!skuUnit.isPromotionApplied()) {
                Optional<Promotion> existPromotion = listPromotion.stream().distinct()
                        .filter(x -> x.getOffer().contains(skuUnit.getSkuId()))
                        .findAny();
                if (existPromotion.isPresent()) {
                    String offer = existPromotion.get().getOffer();
                    if (offer.contains("*")) {
                        int id = offer.indexOf("*");
                        int offerQty = Integer.parseInt(offer.substring(id + 1));
                        int remQty = (skuUnit.getQty() % offerQty);
                        int offerCnt = (skuUnit.getQty() / offerQty);

                        double remPrice = existUnit.get().getPrice() * remQty;
                        double offerPrice = existPromotion.get().getPrice() * offerCnt;

                        double updatedPrice = remPrice + offerPrice;
                        skuUnit.setPrice(updatedPrice);
                        return skuUnit;
                    } else if (offer.contains("&")) {
                        int id = offer.indexOf("&");
                        String skuId1 = offer.substring(0, id);
                        String skuId2 = offer.substring(id + 1);
                        if (existUnit.get().getSkuId().equals(skuId1)) {
                            Optional<SKUUnit> offerUnit = cart.stream().filter(y -> y.getSkuId().equals(skuId2)).findAny();
                            if (offerUnit.isPresent()) {
                                int offerIndex = cart.indexOf(offerUnit.get());
                                if (skuUnit.getQty() == offerUnit.get().getQty()) {
                                    cart.get(offerIndex).setPromotionApplied(true);
                                    double updatedPrice = existPromotion.get().getPrice() * skuUnit.getQty();
                                    skuUnit.setPrice(updatedPrice);
                                    return skuUnit;

                                } else if (skuUnit.getQty() > offerUnit.get().getQty()) {
                                    cart.get(offerIndex).setPromotionApplied(true);

                                    int remQty = skuUnit.getQty() - offerUnit.get().getQty();
                                    double remPrice = existUnit.get().getPrice() * remQty;
                                    double offerPrice = existPromotion.get().getPrice() * offerUnit.get().getQty();
                                    double updatedPrice = offerPrice + remPrice;
                                    skuUnit.setPrice(updatedPrice);
                                    return skuUnit;
                                } else if (skuUnit.getQty() < offerUnit.get().getQty()) {
                                    int remQty = offerUnit.get().getQty() - skuUnit.getQty();
                                    cart.get(offerIndex).setQty(remQty);
                                    cart.get(offerIndex).setPromotionApplied(true);

                                    double updatedPrice = existPromotion.get().getPrice() * skuUnit.getQty();
                                    skuUnit.setPrice(updatedPrice);
                                    return skuUnit;
                                }
                            } else {
                                double updatedPrice = existUnit.get().getPrice() * skuUnit.getQty();
                                skuUnit.setPrice(updatedPrice);
                                return skuUnit;
                            }
                        } else {
                            return null;
                        }
                        return null;
                    } else {
                        throw new Exception("Invalid SKU Unit added to cart!");
                    }
                } else {
                    double updatedPrice = existUnit.get().getPrice() * skuUnit.getQty();
                    skuUnit.setPrice(updatedPrice);
                    return skuUnit;
                }
            }else{
                double updatedPrice = existUnit.get().getPrice() * skuUnit.getQty();
                skuUnit.setPrice(updatedPrice);
                return skuUnit;
            }
        }else{
            throw new Exception("Invalid SKU Unit added to cart!");
        }
    }

    boolean isValidSKUId(String skuId) throws Exception {
        if(!listSKU.contains(skuId)){
            throw new Exception("Invalid SKU Id "+skuId);
        }
        return true;
    }

    boolean isValidQty(int qty) throws Exception {
        if(qty <= 0){
            throw new Exception("Quantity can't be zero");
        }
        return true;
    }

    boolean isValidPrice(double price) throws Exception {
        if(price <= 0){
            throw new Exception("Unit price can't be zero");
        }
        return true;
    }

    boolean isEmpty(String qty) throws Exception {
        if(qty.isEmpty()) {
            throw new Exception("Promotion quantity can't be blank ");
        }
        return false;
    }

    void updateSKUUnitPrice(String skuId, double newPrice) throws Exception {
        if(isValidSKUId(skuId) && isValidPrice(newPrice)) {
            listSKUUnit.add(new SKUUnit(skuId, newPrice));
        }
    }

    void updatePromotion(String offer, double price) throws Exception {
        if (offer.contains("*")) {
            int id = offer.indexOf("*");
            String skuId = offer.substring(0,id);
            String qtyStr = offer.substring(id+1);
            int qty = 0;
            if(!isEmpty(qtyStr)){
                qty = Integer.parseInt(qtyStr);
            }
            if(isValidSKUId(skuId) && isValidQty(qty) && isValidPrice(price)){
                listPromotion.add(new Promotion(offer, price));
            }
        } else if (offer.contains("&")) {
            int id = offer.indexOf("&");
            String skuId1 = offer.substring(0,id);
            String skuId2 = offer.substring(id+1);
            if(isValidSKUId(skuId1) && isValidSKUId(skuId2) && isValidPrice(price)) {
                listPromotion.add(new Promotion(offer, price));
            }
        }else{
            throw new Exception("Invalid promotion "+offer);
        }
    }

    public void initializeSKUPrice() {
        try {
            updateSKUUnitPrice("A", 50);
            updateSKUUnitPrice("B", 30);
            updateSKUUnitPrice("C", 20);
            updateSKUUnitPrice("D", 15);

            listSKUUnit.forEach(x -> System.out.println(x.toString()));
        }catch (Exception e){
            logger.info("Error occurred while initializing SKU price. " + e.getMessage());
        }
    }

    public void initializePromotion() {
        try {
            updatePromotion("A*3", 130);
            updatePromotion("B*2", 45);
            updatePromotion("C&D", 30);

            listPromotion.forEach(x -> System.out.println(x.toString()));
        }catch (Exception e){
            logger.info("Error occurred while initializing promotion. " + e.getMessage());
        }
    }

    private void initializeCart() {
        try {
            addToCart("A", 3);
            addToCart("B", 5);
            addToCart("C", 1);
            addToCart("D", 1);

            cart.forEach(x -> System.out.println(x.toString()));
        }catch (Exception e){
            logger.info("Error occurred while initializing cart. " + e.getMessage());
        }
    }

    private void processCart() throws Exception {
        double finalAmount = 0;
        for (SKUUnit unit :cart){
            SKUUnit updateUnit = updateCart(unit);
            System.out.println(unit.getSkuId()+" - "+unit.getPrice());
            finalAmount += unit.getPrice();
        }
        System.out.println("Final Amount: "+finalAmount);
    }
}
