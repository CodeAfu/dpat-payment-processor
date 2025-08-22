package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class ApplePayPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String touchIdData) {
        System.out.println("Processing Apple Pay Payment");
        System.out.println("Touch ID: " + touchIdData);
        System.out.println("Verifying biometric data...");
        System.out.println("Processing through Apple Pay...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Apple Pay";
    }
}
