package com.ias.dpat.flexible;

import com.ias.dpat.flexible.factory.AbstractPaymentFactory;
import com.ias.dpat.flexible.strategy.PaymentProcessor;
import com.ias.dpat.flexible.types.PaymentType;

public class FlexibleOrder {
    private String productName;
    private double amount;
    private PaymentProcessor paymentProcessor;
    
    public FlexibleOrder(String productName, double amount, AbstractPaymentFactory paymentFactory) {
        this.productName = productName;
        this.amount = amount;
        this.paymentProcessor = new PaymentProcessor(paymentFactory);
    }
    
    public boolean processOrder(PaymentType paymentType, String paymentDetails) {
        System.out.println("Processing order for: " + productName);
        
        try {
            paymentProcessor.setPaymentMethod(paymentType);
            boolean paymentSuccess = paymentProcessor.processPayment(amount, paymentDetails);
            
            // Simulate expired card
            if (paymentDetails.equals("expired-card")) {
                throw new IllegalArgumentException("Your credit card is expired");
            }

            if (paymentSuccess) {
                System.out.println("Order completed successfully using " + 
                                 paymentProcessor.getCurrentPaymentMethod() + "!\n");
                return true;
            } else {
                System.out.println("Payment failed!\n");
                return false;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage() + "\n");
            return false;
        }
    }
    
    public boolean retryWithDifferentPayment(PaymentType paymentType, String paymentDetails) {
        System.out.println("Retrying payment with different method...");
        return processOrder(paymentType, paymentDetails);
    } 
}
