package com.mindhub.homebanking.utils;

public final class CardUtils {

    private CardUtils(){

    }

    public static int getRandomNumber(int min, int max){
        return (int) ((Math.random()* (max - min)) + min);
    }

    public static Integer getCvv() {
        Integer cvv = getRandomNumber(100, 999);
        return cvv;
    }

    public static String getCardNumber() {
        String cardNumber = getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999) + " " + getRandomNumber(1000, 9999);
        return cardNumber;
    }
}
