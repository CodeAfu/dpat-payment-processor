package com.ias.dpat.flexible.factory;

import com.ias.dpat.flexible.payment.CreditCardPayment;
import com.ias.dpat.flexible.payment.ApplePayPayment;
import com.ias.dpat.flexible.payment.BankTransferPayment;
import com.ias.dpat.flexible.payment.CryptocurrencyPayment;
import com.ias.dpat.flexible.payment.GooglePayPayment;
import com.ias.dpat.flexible.payment.PayPalPayment;
import com.ias.dpat.flexible.strategy.PaymentStrategy;
import com.ias.dpat.flexible.types.PaymentType;

public class PaymentFactory extends AbstractPaymentFactory {
    @Override
    public PaymentStrategy createPaymentStrategy(PaymentType paymentType) {
        switch (paymentType) {
            case PaymentType.CREDIT_CARD:
                return new CreditCardPayment();
            case PaymentType.PAYPAL:
                return new PayPalPayment();
            case PaymentType.BANK_TRANSFER:
                return new BankTransferPayment();
            case PaymentType.APPLE_PAY:
                return new ApplePayPayment();
            case PaymentType.GOOGLE_PAY:
                return new GooglePayPayment();
            case PaymentType.CRYPTOCURRENCY:
                return new CryptocurrencyPayment();
            default:
                throw new IllegalArgumentException("Unsupported payment type: " + paymentType);
        }    
    }
}
