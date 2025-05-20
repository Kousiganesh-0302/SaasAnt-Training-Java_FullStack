package com.util;


public class Validator {
    /** Customer IDs must start with 'C' followed by one or more digits. */
    public static boolean isValidCustomerId(String id) {
        return id != null && id.matches("C\\d+");
    }

    /** Mobile numbers must be exactly 10 digits. */
    public static boolean isValidMobile(String mobile) {
        return mobile != null && mobile.matches("\\d{10}");
    }
}

