package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String cardDetails) {
        System.out.println("Processing Credit Card Payment");
        System.out.println("Card: " + cardDetails);
        System.out.println("Validating card number...");
        System.out.println("Checking credit limit...");
        System.out.println("Charging card...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Credit Card";
    }
}
