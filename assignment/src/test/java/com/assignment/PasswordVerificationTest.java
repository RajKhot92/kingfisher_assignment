package com.assignment;

import com.assignment.job.PasswordVerification;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PasswordVerificationTest  extends TestCase {

    final String invalidPasswordMsg = "Sorry, password is never ok. Kindly use at least one uppercase, lowercase and number.";
    final String passwordOkMsg = "Password is ok. But make it strong using at least one uppercase, lowercase and number.";
    final String validPasswordMsg = "Password verification is successful.";
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public PasswordVerificationTest( String testName )
    {
        super( testName );
    }
    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( PasswordVerificationTest.class );
    }

    PasswordVerification verification = new PasswordVerification();

    public void testIsLargerThanEightChars(){
        assertTrue(verification.isLargerThanEightChars("largerThan8"));
        assertFalse(verification.isLargerThanEightChars("less8"));
    }

    public void testIsEmpty(){
        assertTrue(verification.isEmpty(""));
        assertFalse(verification.isEmpty("notEmpty"));
    }

    public void testIsContainUppercaseLetter(){
        assertTrue(verification.isContainUppercaseLetter("Uppercase"));
        assertFalse(verification.isContainUppercaseLetter("lowercase"));
    }

    public void testIsContainLowercaseLetter(){
        assertTrue(verification.isContainLowercaseLetter("lowercase"));
        assertFalse(verification.isContainLowercaseLetter("UPPERCASE"));
    }

    public void testIsContainNumber(){
        assertTrue(verification.isContainNumber("12345678"));
        assertFalse(verification.isContainNumber("no-numbers"));
    }

    public void testValidatePasswordEmptyPassword(){
        assertEquals(invalidPasswordMsg, verification.validatePassword(""));
    }

    public void testValidatePasswordShortPassword(){
        assertEquals(invalidPasswordMsg, verification.validatePassword("test"));
    }

    public void testValidatePasswordNoUppercase(){
        assertEquals(passwordOkMsg, verification.validatePassword("testpassword"));
    }

    public void testValidatePasswordNoLowercase(){
        assertEquals(invalidPasswordMsg, verification.validatePassword("TESTPASSWORD"));
    }

    public void testValidatePasswordNoNumber(){
        assertEquals(passwordOkMsg, verification.validatePassword("TestPassword"));
    }

    public void testValidatePasswordSuccess(){
        assertEquals(validPasswordMsg, verification.validatePassword("TestPassword123"));
    }

    public void testValidatePasswordAdvancedInvalid(){
        assertEquals("Sorry, password is never ok. Password should be of at least 8 characters. Kindly use at least one uppercase, lowercase and number.", verification.validatePasswordAdvanced("TestPwd"));
    }

    public void testValidatePasswordAdvancedSuccess(){
        assertEquals(validPasswordMsg, verification.validatePasswordAdvanced("TestPassword123"));
    }
}
