package com.mangoshine.steam.util;

/**
 * Dealing with currency.
 */
public class CurrencyUtils {
    public static String parseCentsToDollars(int cents) {
        int dollarsPortion = cents / 100;
        int centsPortion = cents % 100;

        return String.format("$%d.%02d", dollarsPortion, centsPortion);
    }
}
