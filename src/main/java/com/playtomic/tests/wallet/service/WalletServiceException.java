package com.playtomic.tests.wallet.service;

/**
 *
 */
public class WalletServiceException extends Exception {
    public WalletServiceException() {
    }

    public WalletServiceException(String message) {
        super(message);
    }

    public WalletServiceException(String message, Exception e) {
        super(message, e);
    }
}
