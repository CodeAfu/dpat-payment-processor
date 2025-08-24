package com.ias.dpat.flexible.factory;

import com.ias.dpat.flexible.payment.CreditCardPayment;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.ias.dpat.flexible.payment.ApplePayPayment;
import com.ias.dpat.flexible.payment.BankTransferPayment;
import com.ias.dpat.flexible.payment.CryptocurrencyPayment;
import com.ias.dpat.flexible.payment.GooglePayPayment;
import com.ias.dpat.flexible.payment.PayPalPayment;
import com.ias.dpat.flexible.strategy.PaymentStrategy;
import com.ias.dpat.flexible.types.PaymentType;

public class PaymentFactory extends AbstractPaymentFactory {
    private final Map<PaymentType, Supplier<PaymentStrategy>> registry = new HashMap<>();

    public PaymentFactory() {
        registry.put(PaymentType.CREDIT_CARD, CreditCardPayment::new);
        registry.put(PaymentType.PAYPAL, PayPalPayment::new);
        registry.put(PaymentType.BANK_TRANSFER, BankTransferPayment::new);
        registry.put(PaymentType.APPLE_PAY, ApplePayPayment::new);
        registry.put(PaymentType.GOOGLE_PAY, GooglePayPayment::new);
        registry.put(PaymentType.CRYPTOCURRENCY, CryptocurrencyPayment::new);
    }

    public PaymentStrategy createPaymentStrategy(PaymentType type) {
        Supplier<PaymentStrategy> supplier = registry.get(type);
        if (supplier == null) {
            throw new IllegalArgumentException("Unsupported payment type: " + type);
        }
        return supplier.get();
    }
}
