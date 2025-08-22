package com.ias.dpat.simple;

public class SimpleOrder {
    private String productName;
    private double amount;
    private PaymentProcessor paymentProcessor;
    
    public SimpleOrder(String productName, double amount) {
        this.productName = productName;
        this.amount = amount;
        this.paymentProcessor = new PaymentProcessor();
    }
    
    public boolean processOrder(String paymentType, String paymentDetails) {
        System.out.println("Processing order for: " + productName);
        boolean paymentSuccess = paymentProcessor.processPayment(amount, paymentType, paymentDetails);
        
        if (paymentSuccess) {
            System.out.println("Order completed successfully!\n");
            return true;
        } else {
            System.out.println("Order failed due to payment issues!\n");
            return false;
        }
    }
}
