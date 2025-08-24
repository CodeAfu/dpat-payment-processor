package com.ias.dpat;

import com.ias.dpat.systems.FlexiblePaymentSystem;
import com.ias.dpat.systems.NaivePaymentSystem;
import com.ias.dpat.systems.PaymentSystem;

public class App {
    public static void main(String[] args) {
        PaymentSystem paymentSystem;

        paymentSystem = new NaivePaymentSystem();

        System.out.println("-".repeat(30));
        System.out.println("NAIVE APPROACH");        
        System.out.println("-".repeat(30));
        paymentSystem.run();

        paymentSystem = new FlexiblePaymentSystem();

        System.out.println("-".repeat(30));
        System.out.println("FLEXIBLE APPROACH");        
        System.out.println("-".repeat(30));
        paymentSystem.run();
    }
}
