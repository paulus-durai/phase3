package com.abc.telecom.billing.util;

import java.util.regex.Pattern;

public class Utilities {

    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    public static String formatCurrency(double amount) {
        return String.format("$%.2f", amount);
    }

    public static String sanitizeInput(String input) {
        return input != null ? input.trim().replaceAll("[<>]", "") : null;
    }

    // Additional utility methods can be added here as needed
}