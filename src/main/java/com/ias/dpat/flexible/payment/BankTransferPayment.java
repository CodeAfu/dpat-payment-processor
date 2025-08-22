package com.ias.dpat.flexible.payment;

import com.ias.dpat.flexible.strategy.PaymentStrategy;

public class BankTransferPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount, String bankAccount) {
        System.out.println("Processing Bank Transfer");
        System.out.println("Bank Account: " + bankAccount);
        System.out.println("Verifying account details...");
        System.out.println("Initiating transfer...");
        return true;
    }
    
    @Override
    public String getPaymentMethodName() {
        return "Bank Transfer";
    }
}
