package com.ias.dpat.systems;

import com.ias.dpat.simple.SimpleOrder;

public class NaivePaymentSystem implements PaymentSystem {
    public void run() {
        SimpleOrder order1 = new SimpleOrder("Gaming Laptop", 1299.99);
        order1.processOrder("CREDIT_CARD", "1234-5678-9012-3456");
        
        SimpleOrder order2 = new SimpleOrder("Design Patterns Book", 49.99);
        order2.processOrder("PAYPAL", "customer@email.com");
        
        SimpleOrder order3 = new SimpleOrder("Wireless Headphones", 199.99);
        order3.processOrder("BANK_TRANSFER", "ACC-789456123");
        
        SimpleOrder order4 = new SimpleOrder("Smartphone", 899.99);
        order4.processOrder("APPLE_PAY", "touch_id_authorized"); // Fails
    }
}
