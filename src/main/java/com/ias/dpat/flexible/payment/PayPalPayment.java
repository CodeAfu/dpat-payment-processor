package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class PayPalPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String email) {
        System.out.println("Processing PayPal Payment");
        System.out.println("PayPal Email: " + email);
        System.out.println("Connecting to PayPal API...");
        System.out.println("Authorizing payment...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "PayPal";
    }
}
