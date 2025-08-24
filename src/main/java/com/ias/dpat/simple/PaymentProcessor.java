package com.ias.dpat.simple;

class PaymentProcessor {
    
    public boolean processPayment(double amount, String paymentType, String paymentDetails) {
        System.out.println("Processing payment of $" + amount);
        
        if (paymentType.equals("CREDIT_CARD")) {
            return processCreditCardPayment(amount, paymentDetails);
        } else if (paymentType.equals("PAYPAL")) {
            return processPayPalPayment(amount, paymentDetails);
        } else if (paymentType.equals("BANK_TRANSFER")) {
            return processBankTransferPayment(amount, paymentDetails);
        } else {
            System.out.println("Unsupported payment type: " + paymentType);
            return false;
        }
    }
    
    private boolean processCreditCardPayment(double amount, String cardDetails) {
        System.out.println("Processing Credit Card Payment");
        System.out.println("Card: " + cardDetails);
        System.out.println("Validating card number...");
        System.out.println("Checking credit limit...");
        System.out.println("Charging card...");
        return true;
    }
    
    private boolean processPayPalPayment(double amount, String email) {
        System.out.println("Processing PayPal Payment");
        System.out.println("PayPal Email: " + email);
        System.out.println("Connecting to PayPal API...");
        System.out.println("Authorizing payment...");
        return true;
    }
    
    private boolean processBankTransferPayment(double amount, String bankAccount) {
        System.out.println("Processing Bank Transfer");
        System.out.println("Bank Account: " + bankAccount);
        System.out.println("Verifying account details...");
        System.out.println("Initiating transfer...");
        return true;
    }
}
