package com.assignment.job;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordVerification {

    public boolean isLargerThanEightChars(String pwd){
        return pwd.length() >= 8;
    }

    public boolean isEmpty(String pwd){
        return pwd.isEmpty();
    }

    public boolean isContainUppercaseLetter(String pwd){
        return Pattern.matches(".*[A-Z].*", pwd);
    }

    public boolean isContainLowercaseLetter(String pwd){
        return Pattern.matches(".*[a-z].*", pwd);
    }

    public boolean isContainNumber(String pwd){
        return Pattern.matches(".*[0-9].*", pwd);
    }

    public String validatePassword(String pwd){
        int count = 0;
        boolean containsLower = false;
        if(!isEmpty(pwd))
            count++;
        if(isLargerThanEightChars(pwd))
            count++;
        if(isContainUppercaseLetter(pwd))
            count++;
        if(isContainLowercaseLetter(pwd)) {
            count++;
            containsLower = true;
        }
        if(isContainNumber(pwd))
            count++;

        if (count == 5){
            return "Password verification is successful.";
        }else if (count >= 3 && containsLower){
            return "Password is ok. But make it strong using at least one uppercase, lowercase and number.";
        }else {
            return "Sorry, password is never ok. Kindly use at least one uppercase, lowercase and number.";
        }
    }

    public String validatePasswordAdvanced(String pwd){
        Pattern pattern = Pattern.compile("[A-Za-z0-9]{9,}+", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pwd);
        if (matcher.matches()){
            return "Password verification is successful.";
        }else {
            return "Sorry, password is never ok. Password should be of at least 8 characters. Kindly use at least one uppercase, lowercase and number.";
        }
    }
}
