package com.digital.wallet.wallet_service.model.entity.enums;

public enum Currency {
    EUR,USD,TRY;

    public static boolean contains(String test) {
        for (Currency c : Currency.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
