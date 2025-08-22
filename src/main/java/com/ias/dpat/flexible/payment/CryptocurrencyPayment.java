package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class CryptocurrencyPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String walletAddress) {
        System.out.println("Processing Cryptocurrency Payment");
        System.out.println("Wallet Address: " + walletAddress);
        System.out.println("Verifying blockchain transaction...");
        System.out.println("Confirming payment...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Cryptocurrency";
    }
}