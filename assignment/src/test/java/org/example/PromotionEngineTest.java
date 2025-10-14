package org.example;

import com.assignment.job.PromotionEngine;
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
}
