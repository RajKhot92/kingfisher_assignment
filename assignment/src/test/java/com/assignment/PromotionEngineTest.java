package com.assignment;

import com.assignment.job.PromotionEngine;
import com.assignment.model.Promotion;
import com.assignment.model.SKUUnit;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PromotionEngineTest  extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PromotionEngineTest( String testName )
    {
        super( testName );
    }
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PromotionEngineTest.class );
    }

    PromotionEngine engine = new PromotionEngine();

    public void testIsValidQty() throws Exception {
        assertTrue(engine.isValidQty(4));
    }

    public void testIsValidQtyThrowsException() {
        try {
            engine.isValidQty(0);
        }catch (Exception e){
            assertEquals("Quantity can't be zero", e.getMessage());
        }
    }

    public void testIsValidPrice() throws Exception {
        assertTrue(engine.isValidPrice(15));
    }

    public void testIsValidPriceThrowsException() {
        try {
            engine.isValidPrice(0);
        }catch (Exception e){
            assertEquals("Unit price can't be zero", e.getMessage());
        }
    }

    public void testIsEmpty() throws Exception {
        assertFalse(engine.isEmpty("15"));
    }

    public void testIsEmptyThrowsException() {
        try {
            engine.isEmpty("");
        }catch (Exception e){
            assertEquals("Promotion quantity can't be blank", e.getMessage());
        }
    }

    public void testIsValidSKUId() throws Exception {
        Field field = PromotionEngine.class.getDeclaredField("listSKU");
        field.setAccessible(true);
        field.set(engine, Arrays.asList("A","B"));
        assertTrue(engine.isValidSKUId("A"));
    }

    public void testIsValidSKUIdThrowsException() {
        try {
            Field field = PromotionEngine.class.getDeclaredField("listSKU");
            field.setAccessible(true);
            field.set(engine, Arrays.asList("A","B"));
            engine.isValidSKUId("D");
        }catch (Exception e){
            assertEquals("Invalid SKU Id D", e.getMessage());
        }
    }

    public void testUpdateSKUUnitPrice() throws Exception {
        Field field = PromotionEngine.class.getDeclaredField("listSKUUnit");
        field.setAccessible(true);
        field.set(engine, new ArrayList<>());
        engine.updateSKUUnitPrice("A", 20);

        List<SKUUnit> skuUnits = (List<SKUUnit>) field.get(engine);
        assertEquals(1, skuUnits.size());
    }

    public void testUpdateSKUUnitPriceInvalidSKUId() {
        try {
            Field field = PromotionEngine.class.getDeclaredField("listSKUUnit");
            field.setAccessible(true);
            field.set(engine, new ArrayList<>());
            engine.updateSKUUnitPrice("99", 20);
        }catch (Exception e){
            assertEquals("Invalid SKU Id 99", e.getMessage());
        }
    }

    public void testUpdateSKUUnitPriceInvalidPrice() {
        try {
            Field field = PromotionEngine.class.getDeclaredField("listSKUUnit");
            field.setAccessible(true);
            field.set(engine, new ArrayList<>());
            engine.updateSKUUnitPrice("A", 0);
        }catch (Exception e){
            assertEquals("Unit price can't be zero", e.getMessage());
        }
    }

    public void testInitializeSKUPrice() throws Exception {
        Field field = PromotionEngine.class.getDeclaredField("listSKUUnit");
        field.setAccessible(true);

        engine.initializeSKUPrice();
        List<SKUUnit> skuUnits = (List<SKUUnit>) field.get(engine);
        assertEquals("A", skuUnits.get(0).getSkuId());
        assertEquals(50.0, skuUnits.get(0).getPrice());
        assertEquals("B", skuUnits.get(1).getSkuId());
        assertEquals(30.0, skuUnits.get(1).getPrice());
        assertEquals("C", skuUnits.get(2).getSkuId());
        assertEquals(20.0, skuUnits.get(2).getPrice());
        assertEquals("D", skuUnits.get(3).getSkuId());
        assertEquals(15.0, skuUnits.get(3).getPrice());
    }

    public void testInitializePromotion() throws Exception {
        Field field = PromotionEngine.class.getDeclaredField("listPromotion");
        field.setAccessible(true);

        engine.initializePromotion();
        List<Promotion> skuUnits = (List<Promotion>) field.get(engine);
        assertEquals("A*3", skuUnits.get(0).getOffer());
        assertEquals(130.0, skuUnits.get(0).getPrice());
        assertEquals("B*2", skuUnits.get(1).getOffer());
        assertEquals(45.0, skuUnits.get(1).getPrice());
        assertEquals("C&D", skuUnits.get(2).getOffer());
        assertEquals(30.0, skuUnits.get(2).getPrice());
    }

    public void testInitializeCart() throws Exception {
        Field field = PromotionEngine.class.getDeclaredField("cart");
        field.setAccessible(true);

        engine.initializeCart();
        List<SKUUnit> skuUnits = (List<SKUUnit>) field.get(engine);
        assertEquals("A", skuUnits.get(0).getSkuId());
        assertEquals(3, skuUnits.get(0).getQty());
        assertEquals("B", skuUnits.get(1).getSkuId());
        assertEquals(5, skuUnits.get(1).getQty());
        assertEquals("C", skuUnits.get(2).getSkuId());
        assertEquals(1, skuUnits.get(2).getQty());
        assertEquals("D", skuUnits.get(3).getSkuId());
        assertEquals(1, skuUnits.get(3).getQty());
    }

    public void testProcessCart() throws Exception {

        assertEquals(0.0, engine.processCart());
    }

    public void testInitialize1() throws Exception {
        engine.initializeSKUPrice();
        engine.initializePromotion();

        SKUUnit unit1 = new SKUUnit("A", 3);
        SKUUnit unit2 = new SKUUnit("B", 5);
        SKUUnit unit3 = new SKUUnit("C", 1);
        SKUUnit unit4 = new SKUUnit("D", 1);

        Field field3 = PromotionEngine.class.getDeclaredField("cart");
        field3.setAccessible(true);
        field3.set(engine, Arrays.asList(unit1, unit2, unit3, unit4));

        assertEquals(280.0, engine.processCart());
    }

    public void testInitializeScenario2() throws Exception {
        engine.initializeSKUPrice();
        engine.initializePromotion();

        SKUUnit unit1 = new SKUUnit("A", 1);
        SKUUnit unit2 = new SKUUnit("B", 1);
        SKUUnit unit3 = new SKUUnit("C", 1);

        Field field3 = PromotionEngine.class.getDeclaredField("cart");
        field3.setAccessible(true);
        field3.set(engine, Arrays.asList(unit1, unit2, unit3));

        assertEquals(100.0, engine.processCart());
    }

    public void testInitializeScenario3() throws Exception {
        engine.initializeSKUPrice();
        engine.initializePromotion();

        SKUUnit unit1 = new SKUUnit("A", 5);
        SKUUnit unit2 = new SKUUnit("B", 5);
        SKUUnit unit3 = new SKUUnit("C", 1);

        Field field3 = PromotionEngine.class.getDeclaredField("cart");
        field3.setAccessible(true);
        field3.set(engine, Arrays.asList(unit1, unit2, unit3));

        assertEquals(370.0, engine.processCart());
    }

    public void testInitializeScenario4() throws Exception {
        engine.initializeSKUPrice();
        engine.initializePromotion();

        SKUUnit unit1 = new SKUUnit("A", 3);
        SKUUnit unit2 = new SKUUnit("B", 2);
        SKUUnit unit3 = new SKUUnit("C", 1);
        SKUUnit unit4 = new SKUUnit("D", 2);

        Field field3 = PromotionEngine.class.getDeclaredField("cart");
        field3.setAccessible(true);
        field3.set(engine, Arrays.asList(unit1, unit2, unit3, unit4));

        assertEquals(220.0, engine.processCart());
    }

    public void testInitializeScenario5() throws Exception {
        engine.initializeSKUPrice();
        engine.initializePromotion();

        SKUUnit unit1 = new SKUUnit("A", 3);
        SKUUnit unit2 = new SKUUnit("B", 2);
        SKUUnit unit3 = new SKUUnit("C", 2);
        SKUUnit unit4 = new SKUUnit("D", 1);

        Field field3 = PromotionEngine.class.getDeclaredField("cart");
        field3.setAccessible(true);
        field3.set(engine, Arrays.asList(unit1, unit2, unit3, unit4));

        assertEquals(225.0, engine.processCart());
    }
}
