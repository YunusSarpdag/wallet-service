package com.digital.wallet.wallet_service.model.entity.enums;

public enum TransactionStatus {
    PENDING, APPROVED, DENIED;

    public static boolean contains(String test) {
        for (TransactionStatus t : TransactionStatus.values()) {
            if (t.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
