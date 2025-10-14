package org.example;

import com.assignment.job.PromotionEngine;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        System.out.println( "Promotion Engine Started!" );
        PromotionEngine engine = new PromotionEngine();
        engine.initialize();
    }
}
