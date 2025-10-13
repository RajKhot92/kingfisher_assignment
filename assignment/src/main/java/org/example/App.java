package org.example;

import com.assignment.PromotionEngine;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {

        System.out.println( "Promotion Engine Started!" );
        PromotionEngine engine = new PromotionEngine();
        engine.initialize();
    }
}
