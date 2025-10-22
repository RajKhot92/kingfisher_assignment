package com.assignment.job;

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

    public void validatePassword(String pwd){
        int count = 0;
        if(isEmpty(pwd))
            count++;
        if(isLargerThanEightChars(pwd))
            count++;
        if(isContainUppercaseLetter(pwd))
            count++;
        if(isContainLowercaseLetter(pwd))
            count++;
        if(isContainNumber(pwd))
            count++;

        if(count >= 3){
            System.out.println("Password is ok.");
        }
//        System.out.println("isLargerThanEightChars= "+isLargerThanEightChars(pwd));
//        System.out.println("isEmpty= "+isEmpty(pwd));
//        System.out.println("isContainUppercaseLetter= "+isContainUppercaseLetter(pwd));
//        System.out.println("isContainLowercaseLetter= "+isContainLowercaseLetter(pwd));
//        System.out.println("isContainNumber= "+isContainNumber(pwd));
    }
}
