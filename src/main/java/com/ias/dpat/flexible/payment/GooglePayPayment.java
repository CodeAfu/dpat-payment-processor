package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class GooglePayPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String googleAccount) {
        System.out.println("Processing Google Pay Payment");
        System.out.println("Google Account: " + googleAccount);
        System.out.println("Authenticating with Google...");
        System.out.println("Processing payment...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Google Pay";
    }
}
