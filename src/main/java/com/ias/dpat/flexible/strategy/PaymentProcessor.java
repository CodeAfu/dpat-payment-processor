package com.ias.dpat.flexible.strategy;

import com.ias.dpat.flexible.factory.AbstractPaymentFactory;
import com.ias.dpat.flexible.types.PaymentType;

public class PaymentProcessor {
    private PaymentStrategy paymentStrategy;
    private AbstractPaymentFactory paymentFactory;
    
    public PaymentProcessor(AbstractPaymentFactory factory) {
        this.paymentFactory = factory;
    }
    
    public void setPaymentMethod(PaymentType paymentType) {
        this.paymentStrategy = paymentFactory.createPaymentStrategy(paymentType);
    }
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.paymentStrategy = strategy;
    }
    
    public boolean processPayment(double amount, String paymentDetails) {
        if (paymentStrategy == null) {
            throw new IllegalStateException("Payment method not set");
        }
        
        System.out.println("Processing payment of $" + amount + " using " + 
                         paymentStrategy.getPaymentMethodName());
        return paymentStrategy.processPayment(amount, paymentDetails);
    }
    
    public String getCurrentPaymentMethod() {
        return paymentStrategy != null ? paymentStrategy.getPaymentMethodName() : "None";
    }
}
