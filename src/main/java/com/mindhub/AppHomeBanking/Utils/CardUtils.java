package com.mindhub.AppHomeBanking.Utils;

public final class CardUtils {
    public static int getRandomNumber(int min, int max) {return (int) ((Math.random() * (max - min)) + min);}
    public static String generateCardNumber() {
        StringBuilder cardNumber; //3973-4475-2239-2248
            cardNumber = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                cardNumber.append(getRandomNumber(0, 9));
                if ((i + 1) % 4 == 0 && i != 15) cardNumber.append("-");
            }
        return cardNumber.toString();
    }

    public static String getCVV() {
        StringBuilder cvvNumber;
        cvvNumber = new StringBuilder();
        for (byte i = 0; i <= 2; i++) {
            cvvNumber.append(getRandomNumber(0, 9));
        }
        return cvvNumber.toString();
    }

    public static String getRandomNumber() {
        int randomNumber = (int) ((Math.random() * 90000000) + 10000000);
        return "VIN-" + randomNumber;
    }
}
