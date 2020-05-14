package com.octo.bankoperations.utils;

public class AccountUtils {

    private AccountUtils() {
    }

    public static String getBankCode(String rib) {
        return rib.substring(0, 3);
    }

}
