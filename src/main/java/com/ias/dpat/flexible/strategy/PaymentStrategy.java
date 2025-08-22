package com.ias.dpat.flexible.strategy;

public interface PaymentStrategy {
    boolean processPayment(double amount, String paymentDetails);
    String getPaymentMethodName();
}
