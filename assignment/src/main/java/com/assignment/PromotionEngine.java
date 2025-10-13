package com.assignment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PromotionEngine {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private final List<String> listSKU = Arrays.asList("A","B","C","D");

    public Map<String, Integer> skuUnitPrice = new HashMap<>();

    public Map<String, Integer> activePromotion = new HashMap<>();

    public void initialize() {
        initializeSKUPrice();
        initializePromotion();
    }

    boolean isValidSKUId(String skuId) throws Exception {
        if(!listSKU.contains(skuId)){
            throw new Exception("Invalid SKU Id "+skuId);
        }
        return true;
    }

    boolean isValidQty(String qty) throws Exception {
        if(qty.isEmpty()) {
            throw new Exception("Promotion quantity can't be blank ");
        }else if(Integer.parseInt(qty) == 0){
            throw new Exception("Promotion quantity can't be zero");
        }
        return true;
    }

    void updateSKUUnitPrice(String skuId, int newPrice) throws Exception {
        if(isValidSKUId(skuId)) {
            skuUnitPrice.put(skuId, newPrice);
        }
    }

    void updatePromotion(String offer, int price) throws Exception {
        if (offer.contains("*")) {
            int id = offer.indexOf("*");
            String skuId = offer.substring(0,id);
            String qty = offer.substring(id+1);
            if(isValidSKUId(skuId)){
                if(isValidQty(qty)) {
                    activePromotion.put(offer, price);
                }
            }
        } else if (offer.contains("&")) {
            int id = offer.indexOf("&");
            String skuId1 = offer.substring(0,id);
            String skuId2 = offer.substring(id+1);
            if(isValidSKUId(skuId1) && isValidSKUId(skuId2)) {
                activePromotion.put(offer, price);
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

            for (Map.Entry<String, Integer> key: skuUnitPrice.entrySet()){
                System.out.println(key.getKey()+"\t"+key.getValue());
            }
        }catch (Exception e){
            logger.info("Error occurred while initializing SKU price. " + e.getMessage());
        }
    }

    public void initializePromotion() {
        try {
            updatePromotion("A*3", 130);
            updatePromotion("B*2", 45);
            updatePromotion("C&D", 30);

            for (Map.Entry<String, Integer> key: activePromotion.entrySet()){
                System.out.println(key.getKey()+"\t"+key.getValue());
            }
        }catch (Exception e){
            logger.info("Error occurred while initializing promotion. " + e.getMessage());
        }
    }
}
