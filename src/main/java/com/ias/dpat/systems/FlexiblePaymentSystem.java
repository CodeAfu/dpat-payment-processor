package com.ias.dpat.systems;

import com.ias.dpat.flexible.FlexibleOrder;
import com.ias.dpat.flexible.factory.AbstractPaymentFactory;
import com.ias.dpat.flexible.factory.PaymentFactory;
import com.ias.dpat.flexible.payment.ApplePayPayment;
import com.ias.dpat.flexible.strategy.PaymentProcessor;
import com.ias.dpat.flexible.types.PaymentType;

public class FlexiblePaymentSystem implements PaymentSystem {
    public void run() {
        AbstractPaymentFactory paymentFactory = new PaymentFactory();
        
        System.out.println("=== Using Standard Payment Factory ===");
        
        FlexibleOrder order1 = new FlexibleOrder("Gaming Laptop", 1299.99, paymentFactory);
        order1.processOrder(PaymentType.CREDIT_CARD, "1234-5678-9012-3456");
        
        FlexibleOrder order2 = new FlexibleOrder("Design Patterns Book", 49.99, paymentFactory);
        order2.processOrder(PaymentType.PAYPAL, "customer@email.com");
        
        FlexibleOrder order3 = new FlexibleOrder("Smartphone", 899.99, paymentFactory);
        order3.processOrder(PaymentType.APPLE_PAY, "touch_id_authorized");
        
        FlexibleOrder order4 = new FlexibleOrder("Wireless Headphones", 199.99, paymentFactory);
        order4.processOrder(PaymentType.CRYPTOCURRENCY, "1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa");
        
        System.out.println("=== Demonstrating Runtime Flexibility ===");
        
        // Retry with different payment
        FlexibleOrder flexibleOrder = new FlexibleOrder("Premium Subscription", 29.99, paymentFactory);
        if (!flexibleOrder.processOrder(PaymentType.CREDIT_CARD, "expired-card")) {
            flexibleOrder.retryWithDifferentPayment(PaymentType.PAYPAL, "backup@email.com");
        }
        
        // Just for demonstration
        System.out.println("=== Direct Strategy Usage ===");
        
        PaymentProcessor processor = new PaymentProcessor(paymentFactory);
        processor.setPaymentStrategy(new ApplePayPayment());
        processor.processPayment(159.99, "face_id_verified");
    }
}
